package com.github.chencye.app.ftp.handler.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.handleConfig.PreHandlerConfig;
import com.github.chencye.app.ftp.handler.Handler;

/**
 * <pre>
 * 下载文件之前的前置操作
 * 1. 判断文件是否已存在，如果需要
 * 2. 为远程文件名添加后缀，如果需要
 * 3. 为本地文件名添加后缀，如果需要
 * </pre>
 * 
 * @author chencye 2017-07-01 22:12:32
 */
public class DownloadPreHandler extends Handler {
    private final static Logger LOGGER = LoggerFactory.getLogger(DownloadPreHandler.class);
    
    private final PreHandlerConfig preHandlerConfig;
    private final boolean enable;
    private final boolean enableCheckExist;
    private final boolean enableRemoteSuffix;
    private final boolean enableLocalSuffix;
    
    public DownloadPreHandler(HostConfig hostConfig, Map<String, Object> container) {
        super(hostConfig, container);
        this.preHandlerConfig = hostConfig.getPreHandler();
        this.enable = preHandlerConfig != null && BooleanUtils.isTrue(preHandlerConfig.getEnable());
        this.enableCheckExist = this.enable && BooleanUtils.isTrue(preHandlerConfig.getEnableCheckExist());
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
     * @param ftp
     * @param filename
     *            文件名
     * @return 文件名数组，远程文件名、本地文件名
     * @throws Exception
     */
    @Override
    public <T> T handle(Object... params) throws Exception {
        int paramsLength = 2;
        if (params == null || params.length != paramsLength) {
            LOGGER.warn("IllegalArgumentException. {}", params);
            throw new IllegalArgumentException();
        }
        
        int paramsIndex = 0;
        return handle((FTP) params[paramsIndex++], (String) params[paramsIndex++]);
    }
    
    /**
     * <pre>
     * 下载文件之前的前置操作
     * 1. 判断文件是否已存在，如果需要
     * 2. 为远程文件名添加后缀，如果需要
     * 3. 为本地文件名添加后缀，如果需要
     * </pre>
     * 
     * @param ftp
     * @param filename
     *            文件名
     * @return 文件名数组，远程文件名、本地文件名
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private <T> T handle(FTP ftp, String filename) throws Exception {
        // 第一个为下载过程中远程文件名，第二个为下载过程中本地文件名
        String[] filenames = { filename, filename };
        if (!enable) {
            return (T) filenames;
        }
        
        // 判断本是否已存在
        if (enableCheckExist) {
            if (Files.exists(Paths.get(super.hostConfig.getLocalDir(), filename))) {
                LOGGER.info("file already exist. {}", filename);
                return null;
            }
        }
        
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
    
}
