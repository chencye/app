package com.github.chencye.app.ftp.handler;

import com.github.chencye.app.ftp.conf.FTPConfig;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.impl.*;
import com.github.chencye.app.ftp.selector.FTPFileSelector;
import com.github.chencye.app.ftp.selector.impl.FTPDownloadFileSelector;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HandlerHolder implements InitializingBean {
    public static final Map<HostConfig, FTPFileSelector> fileSelectors = new ConcurrentHashMap<>();

    public static final Map<HostConfig, Handler> beforeHandlers = new ConcurrentHashMap<>();
    public static final Map<HostConfig, Handler> preHandlers = new ConcurrentHashMap<>();
    public static final Map<HostConfig, Handler> remoteHandlers = new ConcurrentHashMap<>();
    public static final Map<HostConfig, Handler> successHandlers = new ConcurrentHashMap<>();
    public static final Map<HostConfig, Handler> failedHandlers = new ConcurrentHashMap<>();
    public static final Map<HostConfig, Handler> finalHandlers = new ConcurrentHashMap<>();

    private final FTPConfig ftpConfig;

    @Autowired
    public HandlerHolder(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Objects.isNull(ftpConfig) || !ftpConfig.isEnable()) {
            return;
        }

        List<HostConfig> hostConfigs = ftpConfig.getHosts();
        if (CollectionUtils.isEmpty(hostConfigs)) {
            return;
        }

        for (HostConfig hostConfig : hostConfigs) {
            if (Objects.isNull(hostConfig) || BooleanUtils.isFalse(hostConfig.getEnable())) {
                continue;
            }

            fileSelectors.put(hostConfig, new FTPDownloadFileSelector(hostConfig));

            beforeHandlers.put(hostConfig, new FTPBeforeHandler(hostConfig));
            preHandlers.put(hostConfig, new DownloadPreHandler(hostConfig));
            remoteHandlers.put(hostConfig, new RemoteHandler(hostConfig));
            successHandlers.put(hostConfig, new DownloadSuccessHandler(hostConfig));
            failedHandlers.put(hostConfig, new DownLoadFailedHandler(hostConfig));
            finalHandlers.put(hostConfig, new FTPFinalHandler(hostConfig));
        }
    }
}
