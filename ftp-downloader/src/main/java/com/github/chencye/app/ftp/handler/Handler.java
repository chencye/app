package com.github.chencye.app.ftp.handler;

import com.github.chencye.app.ftp.conf.HostConfig;

public abstract class Handler {

    protected final HostConfig hostConfig;

    public Handler(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }

    public abstract <T> T handle(Object... params) throws Exception;

}
