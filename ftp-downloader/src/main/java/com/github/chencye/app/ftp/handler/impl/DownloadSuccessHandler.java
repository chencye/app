package com.github.chencye.app.ftp.handler.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enterprisedt.net.ftp.FTPException;
import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.SuccessHandlerConfig;
import com.github.chencye.app.ftp.handler.Handler;

/**
 * <pre>
 * 一个文件下载完毕后的处理
 * 1. 保存文件名 
 * 2. 保存文件大小
 * 3. 修正远程文件名
 * 4. 备份或删除远程文件
 * 5. 修正本地文件名
 * </pre>
 * 
 * @author chencye 2017-06-12 23:03:50
 */
public class DownloadSuccessHandler extends Handler {
    private final static Logger LOGGER = LoggerFactory.getLogger(DownloadSuccessHandler.class);
    
    private final SuccessHandlerConfig successHandlerConfig;
    private final boolean enable;
    private final boolean enableBak;
    private final boolean enableDelete;
    
    public DownloadSuccessHandler(HostConfig hostConfig, Map<String, Object> container) {
        super(hostConfig, container);
        this.successHandlerConfig = hostConfig.getSuccessHandler();
        this.enable = this.successHandlerConfig != null && BooleanUtils.isTrue(this.successHandlerConfig.getEnable());
        this.enableBak = this.enable && BooleanUtils.isTrue(this.successHandlerConfig.getEnableBak())
                && StringUtils.isNotBlank(this.successHandlerConfig.getBakDir());
        this.enableDelete = this.enable && BooleanUtils.isTrue(this.successHandlerConfig.getEnableDelete());
    }
    
    /**
     * <pre>
     * 一个文件下载完毕后的处理
     * 1. 保存文件名 
     * 2. 保存文件大小
     * 3. 修正远程文件名
     * 4. 备份或删除远程文件
     * 5. 修正本地文件名
     * </pre>
     * 
     * @param ftp
     * @param filename
     *            文件名
     * @param remoteFilename
     *            远程文件名，可能添加了后缀的
     * @param downloadingFilename
     *            本地文件名，可能添加了后缀的
     * @throws Exception
     */
    @Override
    public <T> T handle(Object... params) throws Exception {
        int paramsLength = 4;
        if (params == null || params.length != paramsLength) {
            LOGGER.warn("IllegalArgumentException. {}", params);
            throw new IllegalArgumentException();
        }
        
        int paramsIndex = 0;
        handle((FTP) params[paramsIndex++], (String) params[paramsIndex++], (String) params[paramsIndex++], (String) params[paramsIndex++]);
        return null;
    }
    
    /**
     * <pre>
     * 一个文件下载完毕后的处理
     * 1. 保存文件名 
     * 2. 保存文件大小
     * 3. 修正远程文件名
     * 4. 备份或删除远程文件
     * 5. 修正本地文件名
     * </pre>
     * 
     * @param ftp
     * @param filename
     *            文件名
     * @param remoteFilename
     *            远程文件名，可能添加了后缀的
     * @param downloadingFilename
     *            本地文件名，可能添加了后缀的
     * @throws Exception
     */
    private void handle(FTP ftp, String filename, String remoteFilename, String downloadingFilename) throws Exception {
        this.putFilename(filename);
        this.putFilesize(this.getFilesize(filename));
        
        // 修正远程文件名
        if (!filename.equals(remoteFilename)) {
            ftp.rename(remoteFilename, filename);
        }
        
        // 备份或删除远程文件
        if (enableBak) {
            ftp.move(filename, this.successHandlerConfig.getBakDir());
        } else if (enableDelete) {
            ftp.del(filename);
        }
        
        // 下载完毕后，修正下载到本地后的文件名
        renameLocalFile(downloadingFilename, filename);
    }
    
    private long getFilesize(String filename) throws IOException, FTPException {
        return Files.size(Paths.get(hostConfig.getLocalDir(), filename));
    }
    
    private void renameLocalFile(String downloadingFilename, String filename) {
        if (downloadingFilename.equals(filename)) {
            return;
        }
        String localDir = hostConfig.getLocalDir();
        File downloadingFile = Paths.get(localDir, downloadingFilename).toFile();
        File downloadOverFile = Paths.get(localDir, filename).toFile();
        downloadingFile.renameTo(downloadOverFile);
    }
}
