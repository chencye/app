package com.github.chencye.app.ftp.selector;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.selector.impl.FTPDownloadFileSelector;

public class SelectorFactory {

    public static FTPFileSelector getDownloadFileSelector(HostConfig hostConfig) {
        return new FTPDownloadFileSelector(hostConfig);
    }
}
