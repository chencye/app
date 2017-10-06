package com.github.chencye.app.ftp;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.github.chencye.app.ftp.handler.Handler;
import com.github.chencye.app.ftp.selector.FTPFileSelector;

public class FTP {
    private final static Logger LOGGER = LoggerFactory.getLogger(FTP.class);
    private final FTPClient ftpClient = new FTPClient();
    
    public FTP(String ip, String user, String password) {
        connect(ip, user, password);
    }
    
    private void connect(String ip, String user, String password) {
        try {
            ftpClient.setRemoteHost(ip);
            ftpClient.connect();
            ftpClient.login(user, password);
            ftpClient.setParserLocale(Locale.ENGLISH);
            ftpClient.setConnectMode(FTPConnectMode.PASV);
            ftpClient.setType(FTPTransferType.BINARY);
            LOGGER.info("login success.", ip);
        } catch (IOException | FTPException e) {
            LOGGER.warn("connect error. ip={}, user={}, password={}, error={}", ip, user, password, e);
        }
    }
    
    /**
     * 文件下载
     * 
     * @param remoteDir
     *            远程目录
     * @param fileSelector
     *            文件选择器，决定哪些文件可以下载
     * @param localDir
     *            本地目录，下载文件的保存路径
     * @param preHandler
     *            单个文件下载的前置处理，可以为null，不作处理
     * @param successHandler
     *            单个文件下载成功后的处理，可以为null，不作处理
     * @param failedHandler
     *            单个文件下载失败后的处理，可以为null，不作处理
     */
    public void download(String remoteDir, FTPFileSelector fileSelector, String localDir, Handler preHandler, Handler successHandler,
            Handler failedHandler) throws Exception {
        // 切换远程目录
        ftpClient.chdir(remoteDir);
        LOGGER.debug("chdir to remoteDir[{}].", remoteDir);
        // 由于可能会出现服务器限制一次加载文件的数量，所以需要多次加载，直接没有加载到文件信息
        while (true) {
            // 获取远程目录下的文件名
            List<String> filenames = fileSelector.select(this, remoteDir);
            if (CollectionUtils.isEmpty(filenames)) {
                break;
            }
            LOGGER.info("getRemoteFilenames. size={}", filenames.size());
            download(remoteDir, filenames, localDir, preHandler, successHandler, failedHandler);
        }
    }
    
    private void download(String remoteDir, List<String> filenames, String localDir, Handler preHandler, Handler successHandler,
            Handler failedHandler) throws Exception {
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
                ftpClient.get(Paths.get(localDir, downloadingFilename).toString(), remoteFilename);
            } catch (Exception e) {
                // 下载失败的处理
                if (failedHandler != null) {
                    failedHandler.handle(this, e);
                }
                throw e;
            }
            // 下载完毕后的处理：如文件名修正、备份或删除远程服务器的文件
            if (successHandler != null) {
                successHandler.handle(this, filename, remoteFilename, downloadingFilename);
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
            LOGGER.warn("rename {} to {}. error={}", filename, newname, e);
        }
    }
    
    public void move(String filename, String bakDir) {
        try {
            ftpClient.rename(filename, Paths.get(bakDir, filename).toString());
        } catch (IOException | FTPException e) {
            LOGGER.warn("move {} to {}. error={}", filename, bakDir, e);
        }
    }
    
    public void del(String filename) {
        try {
            ftpClient.delete(filename);
        } catch (IOException | FTPException e) {
            LOGGER.warn("delete {}. error={}", filename, e);
        }
    }
    
    public void close() {
        if (ftpClient.connected()) {
            try {
                ftpClient.quit();
            } catch (IOException | FTPException e) {
                e.printStackTrace();
            }
        }
    }
    
}
