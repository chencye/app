package com.github.chencye.app.ftp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import com.github.chencye.app.ftp.conf.FTPConfig;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.constant.Constant;
import com.github.chencye.app.ftp.task.impl.DownloadTask;
import com.github.chencye.app.ftp.task.impl.UploadTask;

/**
 * <pre>
 * spring scheduling 定时执行任务
 * 线程池，在com.github.chencye.app.ftp.Application中配置
 * </pre>
 * 
 * @author chencye 2017-07-01 22:39:22
 */
@Component
public class ScheduledTask implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    
    @Autowired
    private FTPConfig ftpConfig;
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (final HostConfig hostConfig : ftpConfig.getHosts()) {
            switch (hostConfig.getType()) {
            case Constant.TYPE_DOWNLOAD:
                taskRegistrar.addCronTask(new DownloadTask(hostConfig), hostConfig.getCron());
                break;
            case Constant.TYPE_UPLOAD:
                taskRegistrar.addCronTask(new UploadTask(hostConfig), hostConfig.getCron());
                break;
            default:
                logger.info("unload type: {}", hostConfig.getType());
                break;
            }
            
        }
    }
    
}
