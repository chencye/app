package com.github.chencye.app.ftp.handler.impl;

import java.util.Map;

import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.Handler;

public class FTPBeforeHandler extends Handler {
    
    public FTPBeforeHandler(HostConfig hostConfig, Map<String, Object> container) {
        super(hostConfig, container);
    }
    
}
