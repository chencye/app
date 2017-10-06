package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.SuccessHandlerConfig;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Objects;

/**
 * <pre>
 * 一个文件下载完毕后的处理
 * 1. 修正本地文件名
 * 2. 备份或删除本地文件
 * </pre>
 *
 * @author chencye 2017-06-12 23:03:50
 */
public class DownloadSuccessHandler extends LocalHandler {

    public DownloadSuccessHandler(HostConfig hostConfig) {
        super(hostConfig);
        SuccessHandlerConfig successHandlerConfig = hostConfig.getSuccessHandler();
        super.enable = Objects.nonNull(successHandlerConfig) && BooleanUtils.isTrue(successHandlerConfig.getEnable());
        super.enableFixedFilename =  this.enable && BooleanUtils.isTrue(successHandlerConfig.getEnableFixedFilename());
        super.enableBak = this.enable && BooleanUtils.isTrue(successHandlerConfig.getEnableBak());
        super.bakDir = this.enable ? successHandlerConfig.getBakDir() : super.bakDir;
        super.enableDelete = this.enable && BooleanUtils.isTrue(successHandlerConfig.getEnableDelete());
    }
}
