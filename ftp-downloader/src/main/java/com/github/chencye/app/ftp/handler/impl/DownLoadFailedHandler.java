package com.github.chencye.app.ftp.handler.impl;

import java.util.Map;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.Handler;

public class DownLoadFailedHandler extends Handler {
    
    public DownLoadFailedHandler(HostConfig hostConfig, Map<String, Object> container) {
        super(hostConfig, container);
    }
    
}
