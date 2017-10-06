package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.FTPBeforeHandlerConfig;
import com.github.chencye.app.ftp.conf.handleConfig.FailedHandlerConfig;
import com.github.chencye.app.ftp.conf.handleConfig.SuccessHandlerConfig;
import com.github.chencye.app.ftp.constant.Constant;
import com.github.chencye.app.ftp.handler.Handler;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FTPBeforeHandler extends Handler {
    private final Logger logger = LoggerFactory.getLogger(FTPBeforeHandler.class);

    private final boolean enable;
    private final boolean enableCreateLocalDir;

    public FTPBeforeHandler(HostConfig hostConfig) {
        super(hostConfig);
        FTPBeforeHandlerConfig ftpBeforeHandlerConfig = hostConfig.getBeforeHandler();
        enable = ftpBeforeHandlerConfig != null && BooleanUtils.isTrue(ftpBeforeHandlerConfig.getEnable());
        enableCreateLocalDir = enable && BooleanUtils.isTrue(ftpBeforeHandlerConfig.getEnableCreateLocalDir());
    }

    @Override
    public <T> T handle(Object... params) throws Exception {
        if (!enable) {
            return null;
        }
        logger.debug("before handle begin...");
        doHandle();
        logger.debug("before handle end.");
        return null;
    }

    private void doHandle() throws IOException {
        if (!enableCreateLocalDir) {
            return;
        }

        // 检查本地文件夹是否存在，不存在，则创建
        super.hostConfig.setLocalDir(mkdirs(super.hostConfig.getLocalDir(), Constant.USER_DIR));

        // 检查下载成功时的本地备份文件夹
        SuccessHandlerConfig successHandlerConfig = super.hostConfig.getSuccessHandler();
        if (Objects.nonNull(successHandlerConfig)) {
            successHandlerConfig.setBakDir(mkdirs(successHandlerConfig.getBakDir(), super.hostConfig.getLocalDir()));
        }

        // 检查下载失败时的本地备份文件夹
        FailedHandlerConfig failedHandlerConfig = super.hostConfig.getFailedHandler();
        if (Objects.nonNull(failedHandlerConfig)) {
            failedHandlerConfig.setBakDir(mkdirs(failedHandlerConfig.getBakDir(), super.hostConfig.getLocalDir()));
        }
    }

    private String mkdirs(String dir, String parentDir) throws IOException {
        Path path = Paths.get(dir);
        if (!path.isAbsolute()) {
            path = Paths.get(parentDir, dir);
        }
        if (!Files.isDirectory(path)) {
            logger.info("create local dir[{}].", path);
            Files.createDirectories(path);
        }
        return path.toString();
    }
}
