package com.github.chencye.app.ftp.handler;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.impl.*;

public class HandlerFactory {
    public static Handler getBeforeHandler(HostConfig hostConfig) {
        return new FTPBeforeHandler(hostConfig);
    }

    public static Handler getFinalHandler(HostConfig hostConfig) {
        return new FTPFinalHandler(hostConfig);
    }

    public static Handler getPreHandler(HostConfig hostConfig) {
        return new DownloadPreHandler(hostConfig);
    }

    public static Handler getRemoteHandler(HostConfig hostConfig) {
        return new RemoteHandler(hostConfig);
    }

    public static Handler getSuccessHandler(HostConfig hostConfig) {
        return new DownloadSuccessHandler(hostConfig);
    }

    public static Handler getFailedHandler(HostConfig hostConfig) {
        return new DownLoadFailedHandler(hostConfig);
    }

}
