package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.RemoteHandlerConfig;
import com.github.chencye.app.ftp.handler.Handler;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * <pre>
 * 一个文件操作完毕后的，对远程文件的后缀操作
 * 1. 修改文件名
 * 2. 备份或删除远程文件
 * </pre>
 */
public class RemoteHandler extends Handler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RemoteHandlerConfig remoteHandlerConfig;
    private final boolean enable;
    private final boolean enableFixedFilename;
    private final boolean enableBak;
    private final boolean enableDelete;

    public RemoteHandler(HostConfig hostConfig) {
        super(hostConfig);
        this.remoteHandlerConfig = hostConfig.getRemoteHandler();
        this.enable = Objects.nonNull(this.remoteHandlerConfig) && BooleanUtils.isTrue(this.remoteHandlerConfig.getEnable());
        this.enableFixedFilename = this.enable && BooleanUtils.isTrue(this.remoteHandlerConfig.getEnableFixedFilename());
        this.enableBak = this.enable && BooleanUtils.isTrue(this.remoteHandlerConfig.getEnableBak());
        this.enableDelete = this.enable && BooleanUtils.isTrue(this.remoteHandlerConfig.getEnableDelete());
    }

    /**
     * <pre>
     * 一个文件操作完毕后的，对远程文件的后缀操作
     * 1. 修改文件名
     * 2. 备份或删除远程文件
     * </pre>
     *
     * @param params：FTP、原文件名、操作过程中的远程文件名
     */
    @Override
    public <T> T handle(Object... params) throws Exception {
        if (!enable) {
            return null;
        }
        logger.debug("handle begin...");
        int paramsIndex = 0;
        FTP ftp = (FTP) params[paramsIndex++];
        String filename = (String) params[paramsIndex++];
        String remoteFilename = (String) params[paramsIndex];
        doHandle(ftp, filename, remoteFilename);
        logger.debug("handle end.");
        return null;
    }

    private void doHandle(FTP ftp, String filename, String remoteFilename) throws Exception {
        renameRemoteFile(ftp, filename, remoteFilename);
        // 备份或删除本地文件
        if (enableBak) {
            String bakDir = this.remoteHandlerConfig.getBakDir();
            logger.debug("bak remote file. bakDir={}, filename={}", bakDir, filename);

            Path bakPath = Paths.get(bakDir, filename);
            if (!bakPath.isAbsolute()) {
                bakPath = Paths.get(this.hostConfig.getRemoteDir(), bakDir, filename);
            }
            ftp.move(Paths.get(this.hostConfig.getRemoteDir(), filename).toString(), bakPath.toString());
        } else if (enableDelete) {
            logger.debug("delete remote file. filename={}", filename);
            ftp.del(filename);
        }
    }

    private void renameRemoteFile(FTP ftp, String filename, String remoteFilename) {
        if (!enableFixedFilename || Objects.equals(filename, remoteFilename)) {
            return;
        }
        logger.debug("renameRemoteFile. filename={}, remoteFilename={}", filename, remoteFilename);
        // 修正远程文件名
        ftp.rename(remoteFilename, filename);
    }
}
