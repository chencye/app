package com.github.chencye.app.ftp.selector.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.FileSelectConfig;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.selector.FTPFileSelector;

/**
 * <pre>
 * 文件下载时，获取到远程服务器目录上的文件信息后，判断各文件是否需要下载
 * 1. 获取并判断需要下载的文件
 * 2. 临时保存文件最后修改时间
 * </pre>
 * 
 * @author chencye 2017-06-12 21:07:10
 */
public class FTPDownloadFileSelector extends FTPFileSelector {
    private static final Logger logger = LoggerFactory.getLogger(FTPDownloadFileSelector.class);
    
    private final boolean enable;
    private final FileSelectConfig fileSelectConfig;
    
    private int batchCount;
    
    public FTPDownloadFileSelector(HostConfig hostConfig, Map<String, Object> container) {
        super(hostConfig, container);
        this.fileSelectConfig = this.hostConfig.getFileSelect();
        enable = this.fileSelectConfig != null && BooleanUtils.isTrue(this.fileSelectConfig.getEnable());
    }
    
    @Override
    public List<String> select(Object... params) throws IOException, FTPException, ParseException {
        if (params == null || params.length < 2) {
            throw new IllegalArgumentException();
        }
        return select((FTP) params[0], (String) params[1]);
    }
    
    private List<String> select(FTP ftp, String remoteDir) throws IOException, FTPException, ParseException {
        // 配置未启用，返回所有文件
        if (!enable) {
            return Arrays.asList(ftp.dir());
        }
        
        // 是否限制加载多少次
        if (BooleanUtils.isTrue(fileSelectConfig.getEnableLimitBatchCount())) {
            if (batchCount >= fileSelectConfig.getMaxBatchCount()) {
                logger.info("limit batch count. batchCount={}", batchCount);
                batchCount = 0;
                return null;
            }
            batchCount++;
        }
        
        List<String> list = new ArrayList<>();
        // 如果不需要获取其它文件信息，则，只校验文件名
        if (BooleanUtils.isFalse(fileSelectConfig.getEnableGetFileInfo())) {
            String[] filenames = ftp.dir();
            for (String filename : filenames) {
                if (!checkFilename(filename)) {
                    continue;
                }
                list.add(filename);
                if (!checkListSize(list)) {
                    break;
                }
            }
            return list;
        }
        
        // 获取远程目录中的文件信息
        FTPFile[] ftpFiles = ftp.dirDetails(remoteDir);
        if (ArrayUtils.isEmpty(ftpFiles)) {
            return null;
        }
        
        for (FTPFile ftpFile : ftpFiles) {
            // 获取到文件信息后，判断是否真的需要下载
            if (!this.enable(ftpFile)) {
                continue;
            }
            // 临时保存文件最后修改时间
            this.putLastModified(ftpFile.lastModified().getTime());
            list.add(ftpFile.getName());
            if (!checkListSize(list)) {
                break;
            }
        }
        return list;
    }
    
    private boolean checkFilename(String filename) {
        return fileSelectConfig.getFileNameRegex().matcher(filename).matches();
    }
    
    /**
     * 检查文件名列表大小是否达到了设置的最大数量
     * 
     * @param list
     *            文件名列表
     * @return
     */
    private boolean checkListSize(List<String> list) {
        if (BooleanUtils.isFalse(fileSelectConfig.getEnableLimitCount())) {
            return true;
        }
        
        if (list.size() >= fileSelectConfig.getMaxFileCount()) {
            logger.info("limit file count. fileCount={}", list.size());
            return false;
        }
        
        return true;
    }
    
    /**
     * 判断文件是否需要下载
     * 
     * @param ftpFile
     *            文件信息
     * @return
     */
    public boolean enable(FTPFile ftpFile) {
        FileSelectConfig fileSelectConfig = hostConfig.getFileSelect();
        
        String filename = ftpFile.getName();
        // 使用正则校验文件名
        if (!checkFilename(filename)) {
            return false;
        }
        
        // 判断是否需要比较文件最后修改时间
        if (BooleanUtils.isFalse(fileSelectConfig.getEnableCompareTime()) || fileSelectConfig.getMillisecond() == null) {
            return true;
        }
        
        // 比较文件最后修改时间
        long lastModified = ftpFile.lastModified().getTime();
        if (System.currentTimeMillis() - lastModified < fileSelectConfig.getMillisecond()) {
            return false;
        }
        
        return true;
    }
    
}
