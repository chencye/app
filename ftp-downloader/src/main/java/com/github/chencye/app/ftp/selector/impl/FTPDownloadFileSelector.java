package com.github.chencye.app.ftp.selector.impl;

import com.enterprisedt.net.ftp.FTPFile;
import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.conf.selector.FileSelectConfig;
import com.github.chencye.app.ftp.conf.selector.LoopConfig;
import com.github.chencye.app.ftp.conf.selector.SizeCompareConfig;
import com.github.chencye.app.ftp.conf.selector.TimeCompareConfig;
import com.github.chencye.app.ftp.selector.FTPFileSelector;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 文件下载时，获取到远程服务器目录上的文件信息后，判断各文件是否需要下载
 * 1. 获取并判断需要下载的文件
 *
 * 注意：非线程完全。查看属性loopCount
 * </pre>
 *
 * @author chencye 2017-06-12 21:07:10
 */
public class FTPDownloadFileSelector extends FTPFileSelector {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int loopCount; // 循环次数，为0时首次进入，其它为循环节点

    private FileSelectConfig fileSelectConfig;
    private boolean enable;
    private TimeCompareConfig timeCompareConfig;
    private boolean enableTimeCompare;
    private SizeCompareConfig sizeCompareConfig;
    private boolean enableSizeCompare;
    private LoopConfig loopConfig;
    private boolean enableLoop;

    public FTPDownloadFileSelector(HostConfig hostConfig) {
        super(hostConfig);
        fileSelectConfig = hostConfig.getFileSelect();
        enable = Objects.nonNull(this.fileSelectConfig) && BooleanUtils.isTrue(this.fileSelectConfig.getEnable());
        if (enable) {
            timeCompareConfig = this.fileSelectConfig.getTimeCompare();
            enableTimeCompare = Objects.nonNull(this.timeCompareConfig) && BooleanUtils.isTrue(this.timeCompareConfig.getEnable());
            sizeCompareConfig = this.fileSelectConfig.getSizeCompare();
            enableSizeCompare = Objects.nonNull(this.sizeCompareConfig) && BooleanUtils.isTrue(this.sizeCompareConfig.getEnable());
            loopConfig = this.fileSelectConfig.getLoop();
            enableLoop = Objects.nonNull(this.loopConfig) && BooleanUtils.isTrue(this.loopConfig.getEnable());
        }
    }

    /**
     * @param params FTP、远程路径
     */
    @Override
    public List<String> select(Object... params) throws Exception {
        logger.debug("select begin...");
        FTP ftp = (FTP) params[0];
        List<String> list = doSelect(ftp);
        logger.info("getRemoteFilenames. size={}", list == null ? 0 : list.size());
        logger.debug("select end.");
        return list;
    }

    private List<String> doSelect(FTP ftp) throws Exception {
        // 配置未启用，返回所有文件
        if (!enable) {
            return Arrays.asList(ftp.dir());
        }

        // 是否可以继续循环
        if (!isContinueLoop()) {
            // 超过循环限制，返回空，结束当前定时任务
            return null;
        }

        // 循环次数+1
        loopCount++;

        if (enableTimeCompare || enableSizeCompare) {
            // 要比较最后修改时间与文件大小，所以需要加载远程文件详细信息
            return loadByDetails(ftp, this.hostConfig.getRemoteDir());
        }

        // 只加载文件名
        return loadByName(ftp);
    }

    private boolean isContinueLoop() {
        // 首次进入选择器，非循环，返回继续
        if (loopCount == 0) {
            return true;
        }
        // 不允许循环，返回中断
        if (!enableLoop) {
            return false;
        }
        // 如果不限制循环，则返回继续
        if (BooleanUtils.isFalse(loopConfig.getLimitLoopCount()) || Objects.isNull(loopConfig.getMaxLoop())) {
            return true;
        }
        // 未超过循环限制，返回继续
        if (loopCount <= loopConfig.getMaxLoop()) {
            return true;
        }

        // 结束循环
        logger.info("too much loop. loopCount={}", loopCount);
        loopCount = 0;
        return false;
    }

    private List<String> loadByDetails(FTP ftp, String remoteDir) throws Exception {
        // 获取远程目录中的文件信息
        FTPFile[] ftpFiles = ftp.dirDetails(remoteDir);
        if (ArrayUtils.isEmpty(ftpFiles)) {
            return null;
        }

        int fileNumber = 0;
        List<String> list = new ArrayList<>();
        for (FTPFile ftpFile : ftpFiles) {
            // 获取到文件信息后，判断是否真的需要下载
            if (!this.enable(ftpFile)) {
                continue;
            }
            list.add(ftpFile.getName());
            fileNumber++;
            if (!checkFileNumber(fileNumber)) {
                break;
            }
        }
        return list;
    }

    /**
     * 判断文件是否需要下载
     *
     * @param ftpFile 文件信息
     */
    public boolean enable(FTPFile ftpFile) {
        // 文件名校验、最后修改时间比较、文件大小比较
        return checkFilename(ftpFile.getName()) && checkTimeCompare(ftpFile) && checkSizeCompare(ftpFile);
    }

    /**
     * 文件名正则校验
     *
     * @param filename 文件名
     */
    private boolean checkFilename(String filename) {
        return fileSelectConfig.getFileNameRegex().matcher(filename).matches();
    }

    /**
     * 比较远程文件的最后修改时间
     */
    private boolean checkTimeCompare(FTPFile ftpFile) {
        // 不需要比较时间，返回继续
        if (!enableTimeCompare || Objects.isNull(timeCompareConfig.getMillisecond())) {
            return true;
        }
        // 比较文件最后修改时间
        long lastModified = ftpFile.lastModified().getTime();
        // 文件最后修改时间与当前时间的间隔
        long timeAwait = System.currentTimeMillis() - lastModified;
        // 取最近的文件
        if (BooleanUtils.isTrue(timeCompareConfig.getLatest())) {
            return timeAwait <= timeCompareConfig.getMillisecond();
        }
        // 取间隔以前的文件
        return timeAwait > timeCompareConfig.getMillisecond();
    }

    /**
     * 比较远程文件的大小
     */
    private boolean checkSizeCompare(FTPFile ftpFile) {
        // 不需要比较文件大小，返回继续
        if (!enableSizeCompare || Objects.isNull(sizeCompareConfig.getSize())) {
            return true;
        }
        // 远程文件大小
        long fileSize = ftpFile.size();
        // 文件大小临界值
        long compareSize = sizeCompareConfig.getSize();
        // 取较小的文件
        if (BooleanUtils.isTrue(sizeCompareConfig.getSmallest())) {
            return fileSize <= compareSize;
        }
        // 取较大的文件
        return fileSize > compareSize;
    }

    /**
     * 检查文件名列表大小是否达到了设置的最大数量
     *
     * @param size 当前获取到的文件名数量
     */
    private boolean checkFileNumber(int size) {
        // 未配置最大值，返回所有
        if (Objects.isNull(fileSelectConfig.getMaxCount())) {
            return true;
        }
        if (size <= fileSelectConfig.getMaxCount()) {
            return true;
        }
        logger.info("limit file count. fileCount={}", size);
        return false;
    }

    /**
     * 通过文件名比较，即可获取到需要下载的文件名列表
     *
     * @param ftp FTP操作类
     */
    private List<String> loadByName(FTP ftp) throws Exception {
        String[] filenames = ftp.dir();
        if (ArrayUtils.isEmpty(filenames)) {
            return null;
        }

        int size = 0;
        List<String> list = new ArrayList<>();
        for (String filename : filenames) {
            // 检查文件名
            if (!checkFilename(filename)) {
                continue;
            }
            list.add(filename);
            size++;
            // 检查当前获取的文件名数量
            if (!checkFileNumber(size)) {
                break;
            }
        }
        return list;
    }

}
