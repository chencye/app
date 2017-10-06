package com.github.chencye.app.ftp;

import com.enterprisedt.net.ftp.*;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.Handler;
import com.github.chencye.app.ftp.handler.HandlerFactory;
import com.github.chencye.app.ftp.selector.FTPFileSelector;
import com.github.chencye.app.ftp.selector.SelectorFactory;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.print.attribute.standard.JobMessageFromOperator;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class FTP {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final FTPClient ftpClient = new FTPClient();

    private FTPFileSelector selector;
    private Handler preHandler;
    private Handler remoteHandler;
    private Handler successHandler;
    private Handler failedHandler;

    public FTP(HostConfig hostConfig) {
        String ip = hostConfig.getIp();
        String user = hostConfig.getUsername();
        String password = hostConfig.getPassword();
        connect(ip, user, password);
        initSelector(hostConfig);
        initHandler(hostConfig);
    }

    private void initSelector(HostConfig hostConfig) {
        selector = SelectorFactory.getDownloadFileSelector(hostConfig);
    }

    private void initHandler(HostConfig hostConfig) {
        preHandler = HandlerFactory.getPreHandler(hostConfig);
        remoteHandler = HandlerFactory.getRemoteHandler(hostConfig);
        successHandler = HandlerFactory.getSuccessHandler(hostConfig);
        failedHandler = HandlerFactory.getFailedHandler(hostConfig);
    }

    private void connect(String ip, String user, String password) {
        try {
            ftpClient.setRemoteHost(ip);
            ftpClient.connect();
            ftpClient.login(user, password);
            ftpClient.setParserLocale(Locale.ENGLISH);
            ftpClient.setConnectMode(FTPConnectMode.PASV);
            ftpClient.setType(FTPTransferType.BINARY);
            logger.info("login success.");
        } catch (IOException | FTPException e) {
            logger.warn("connect error. ip={}, user={}, password={}, error={}", ip, user, password, e);
        }
    }

    /**
     * 文件下载
     *
     * @param remoteDir 远程目录
     * @param localDir  本地目录，下载文件的保存路径
     */
    public void download(String remoteDir, String localDir) throws Exception {
        // 切换远程目录
        ftpClient.chdir(remoteDir);
        logger.info("go to remoteDir[{}].", remoteDir);
        // 由于可能会出现服务器限制一次加载文件的数量，所以需要多次加载，直接没有加载到文件信息
        while (true) {
            // 获取远程目录下的文件名
            List<String> filenames = selector.select(this);
            if (CollectionUtils.isEmpty(filenames)) {
                break;
            }
            doDownload(filenames, localDir);
        }
    }

    private void doDownload(List<String> filenames, String localDir) throws Exception {
        // 遍历需要下载的文件名，并执行下载
        for (String filename : filenames) {
            // 便于识别正在下载的文件，避免被误删除，在下载完毕后，再将文件名修改回去
            String remoteFilename = filename;
            String downloadingFilename = filename;
            try {
                if (preHandler != null) {
                    String[] names = preHandler.handle(this, filename);
                    if (ArrayUtils.isEmpty(names)) {
                        continue;
                    }
                    remoteFilename = names[0];
                    downloadingFilename = names[1];
                }
                logger.debug("downloading... filename={}, localDir={}, downloadingFilename={}, remoteFilename={}");
                ftpClient.get(Paths.get(localDir, downloadingFilename).toString(), remoteFilename);
            } catch (Exception e) {
                // 下载失败的处理
                if (failedHandler != null) {
                    failedHandler.handle(filename, downloadingFilename);
                }
                continue;
            } finally {
                if (remoteHandler != null) {
                    remoteHandler.handle(this, filename, remoteFilename);
                }
            }
            // 下载完毕后的处理：如文件名修正、备份或删除远程服务器的文件
            if (successHandler != null) {
                successHandler.handle(filename, downloadingFilename);
            }
        }
    }

    public FTPFile[] dirDetails(String remoteDir) throws IOException, FTPException, ParseException {
        return ftpClient.dirDetails(remoteDir);
    }

    public String[] dir() throws IOException, FTPException {
        return ftpClient.dir();
    }

    public void rename(String filename, String newname) {
        try {
            ftpClient.rename(filename, newname);
        } catch (IOException | FTPException e) {
            logger.warn("rename {} to {}. error={}", filename, newname, e);
        }
    }

    public void move(String from, String to) {
        try {
            ftpClient.rename(from, to);
        } catch (IOException | FTPException e) {
            logger.warn("move {} to {}. error={}", from, to, e);
        }
    }

    public void del(String filename) {
        try {
            ftpClient.delete(filename);
        } catch (IOException | FTPException e) {
            logger.warn("delete {}. error={}", filename, e);
        }
    }

    public void close() {
        if (ftpClient.connected()) {
            try {
                logger.info("close ftp.");
                ftpClient.quit();
            } catch (IOException | FTPException e) {
                logger.warn("close ftp error.", e);
            }
        }
    }

}
