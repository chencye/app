package com.github.chencye.app.ftp.handler.impl;

import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.PreHandlerConfig;
import com.github.chencye.app.ftp.handler.Handler;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <pre>
 * 下载文件之前的前置操作
 * 1. 判断文件在本地是否已存在，如果需要
 * 2. 为远程文件名添加后缀，如果需要
 * 3. 为本地文件名添加后缀，如果需要
 * </pre>
 *
 * @author chencye 2017-07-01 22:12:32
 */
public class DownloadPreHandler extends Handler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PreHandlerConfig preHandlerConfig;
    private final boolean enable;
    private final boolean enableCheckExist;
    private final boolean deleteWhenExist;
    private final boolean enableRemoteSuffix;
    private final boolean enableLocalSuffix;

    public DownloadPreHandler(HostConfig hostConfig) {
        super(hostConfig);
        this.preHandlerConfig = hostConfig.getPreHandler();
        this.enable = preHandlerConfig != null && BooleanUtils.isTrue(preHandlerConfig.getEnable());
        this.enableCheckExist = this.enable && BooleanUtils.isTrue(preHandlerConfig.getEnableCheckExist());
        this.deleteWhenExist = this.enable && BooleanUtils.isTrue(preHandlerConfig.getDeleteWhenExist());
        this.enableRemoteSuffix = this.enable && BooleanUtils.isTrue(preHandlerConfig.getEnableRemoteSuffix());
        this.enableLocalSuffix = this.enable && BooleanUtils.isTrue(preHandlerConfig.getEnableLocalSuffix());
    }

    /**
     * <pre>
     * 下载文件之前的前置操作
     * 1. 判断文件是否已存在，如果需要
     * 2. 为远程文件名添加后缀，如果需要
     * 3. 为本地文件名添加后缀，如果需要
     * </pre>
     *
     * @param params FTP、原文件名
     * @return 文件名数组：远程文件名、本地文件名
     */
    @Override
    public <T> T handle(Object... params) throws Exception {
        if (!enable) {
            return null;
        }
        logger.debug("handle begin...");
        int paramsIndex = 0;
        FTP ftp = (FTP) params[paramsIndex++];
        String filename = (String) params[paramsIndex];
        T t = handle(ftp, filename);
        logger.debug("handle end.");
        return t;
    }

    /**
     * <pre>
     * 下载文件之前的前置操作
     * 1. 判断文件是否已存在，如果需要
     * 2. 为远程文件名添加后缀，如果需要
     * 3. 为本地文件名添加后缀，如果需要
     * </pre>
     *
     * @return 文件名数组，远程文件名、本地文件名
     */
    @SuppressWarnings("unchecked")
    private <T> T handle(FTP ftp, String filename) throws Exception {
        // 判断本是否已存在，已存在时，返回空跳过
        if (isExist(filename)) {
            return null;
        }

        // 第一个为下载过程中远程文件名，第二个为下载过程中本地文件名
        String[] filenames = {filename, filename};

        // 下载过程中，是否为远程文件名添加后缀
        if (enableRemoteSuffix) {
            String newRemoteName = filenames[0] + preHandlerConfig.getRemoteSuffix();
            ftp.rename(filenames[0], newRemoteName);
            filenames[0] = newRemoteName;
        }

        // 下载过程中，是否为本地文件名添加后缀
        if (enableLocalSuffix) {
            String newLocalName = filenames[1] + preHandlerConfig.getLocalSuffix();
            filenames[1] = newLocalName;
        }

        return (T) filenames;
    }

    private boolean isExist(String filename) throws IOException {
        if (!enableCheckExist) {
            return false;
        }

        Path localFile = Paths.get(super.hostConfig.getLocalDir(), filename);
        if (!Files.exists(localFile)) {
            return false;
        }

        if (!deleteWhenExist) {
            logger.warn("file already exist. skip[{}]", filename);
            return true;
        }

        logger.warn("file already exist. delete[{}]", filename);
        Files.delete(localFile);
        return false;
    }

}
