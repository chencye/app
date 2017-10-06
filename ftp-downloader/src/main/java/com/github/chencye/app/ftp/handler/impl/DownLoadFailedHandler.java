package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.FailedHandlerConfig;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Objects;

/**
 * <pre>
 * 一个文件下载失败后的处理
 * 1. 修正本地文件名
 * 2. 备份或删除本地文件
 * </pre>
 */
public class DownLoadFailedHandler extends LocalHandler {

    public DownLoadFailedHandler(HostConfig hostConfig) {
        super(hostConfig);
        FailedHandlerConfig failedHandlerConfig = hostConfig.getFailedHandler();
        super.enable = Objects.nonNull(failedHandlerConfig) && BooleanUtils.isTrue(failedHandlerConfig.getEnable());
        super.enableFixedFilename = this.enable && BooleanUtils.isTrue(failedHandlerConfig.getEnableFixedFilename());
        super.enableBak = this.enable && BooleanUtils.isTrue(failedHandlerConfig.getEnableBak());
        super.bakDir = this.enable ? failedHandlerConfig.getBakDir() : super.bakDir;
        super.enableDelete = this.enable && BooleanUtils.isTrue(failedHandlerConfig.getEnableDelete());
    }

}
