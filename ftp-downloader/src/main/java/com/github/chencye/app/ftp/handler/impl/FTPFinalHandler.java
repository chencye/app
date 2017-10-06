package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.FTPFinalHandlerConfig;
import com.github.chencye.app.ftp.handler.Handler;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 一次定时任务完成后的操作，关闭FTP
 */
public class FTPFinalHandler extends Handler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FTPFinalHandlerConfig ftpFinalHandlerConfig;
    private final boolean enable;
    private final boolean enableCloseFTP;

    public FTPFinalHandler(HostConfig hostConfig) {
        super(hostConfig);
        ftpFinalHandlerConfig = hostConfig.getFinalHandler();
        enable = ftpFinalHandlerConfig != null && BooleanUtils.isTrue(ftpFinalHandlerConfig.getEnable());
        enableCloseFTP = enable && BooleanUtils.isTrue(ftpFinalHandlerConfig.getEnableCloseFTP());
    }

    /**
     * 一次定时任务完成后的操作，关闭FTP
     *
     * @param params FTP
     */
    @Override
    public <T> T handle(Object... params) throws Exception {
        if (!enable) {
            return null;
        }

        logger.debug("final handle begin...");
        FTP ftp = (FTP) params[0];
        doHandle(ftp);
        logger.debug("final handle end.");
        return null;
    }

    private void doHandle(FTP ftp) {
        if (enableCloseFTP && Objects.nonNull(ftp)) {
            ftp.close();
        }
    }

}
