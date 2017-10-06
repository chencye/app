package com.github.chencye.app.ftp.selector;

import com.github.chencye.app.ftp.conf.HostConfig;

import java.util.List;

/**
 * <pre>
 * 文件选择器
 * 提供符合要求，可以进行FTP操作的文件名
 * </pre>
 *
 * @author chencye 2017-06-12 20:59:19
 */
public abstract class FTPFileSelector {

    protected final HostConfig hostConfig;

    public FTPFileSelector(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }

    public abstract List<String> select(Object... params) throws Exception;
}
