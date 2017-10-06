package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 对本地文件的操作，提供给DownloadSuccessHandler与DownLoadFailedHandler继承
 * <pre>
 * 一个文件下载完毕后的处理
 * 1. 修正本地文件名
 * 2. 备份或删除本地文件
 * </pre>
 */
public class LocalHandler extends Handler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    boolean enable;
    boolean enableFixedFilename;
    boolean enableBak;
    String bakDir;
    boolean enableDelete;

    LocalHandler(HostConfig hostConfig) {
        super(hostConfig);
    }

    /**
     * <pre>
     * 一个文件下载完毕后的处理
     * 1. 修正本地文件名
     * 2. 备份或删除本地文件
     * </pre>
     *
     * @param params：原文件名、操作过程中本地文件名
     */
    @Override
    public <T> T handle(Object... params) throws Exception {
        if (!enable) {
            return null;
        }
        logger.debug("handle begin...");
        int paramsIndex = 0;
        String filename = (String) params[paramsIndex++];
        String downloadingFilename = (String) params[paramsIndex];
        doHandle(bakDir, filename, downloadingFilename);
        logger.debug("handle end.");
        return null;
    }

    protected void doHandle(String bakDir, String filename, String downloadingFilename) throws Exception {
        // 下载完毕后，修正下载到本地后的文件名
        renameLocalFile(filename, downloadingFilename);
        // 备份或删除远程文件
        if (enableBak) {
            logger.debug("bak local file. bakDir={}, filename={}", bakDir, filename);
            Path source = Paths.get(this.hostConfig.getLocalDir(), filename);
            Path target = Paths.get(bakDir, filename);
            Files.move(source, target);
        } else if (enableDelete) {
            logger.debug("delete local file. filename={}", filename);
            Path source = Paths.get(this.hostConfig.getLocalDir(), filename);
            Files.deleteIfExists(source);
        }
    }

    private void renameLocalFile(String filename, String downloadingFilename) throws IOException {
        if (!enableFixedFilename || Objects.equals(filename, downloadingFilename)) {
            return;
        }
        logger.debug("renameLocalFile. filename={}, downloadingFilename={}", filename, downloadingFilename);
        // 下载完毕后，修正下载到本地后的文件名
        Path source = Paths.get(hostConfig.getLocalDir(), downloadingFilename);
        Path target = Paths.get(hostConfig.getLocalDir(), filename);
        Files.move(source, target);
    }

}
