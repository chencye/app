package com.github.chencye.app.ftp;

import com.github.chencye.app.ftp.conf.FTPConfig;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.task.DownloadTask;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    private final FTPConfig ftpConfig;

    @Autowired
    public ScheduledTask(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (Objects.isNull(ftpConfig) || !ftpConfig.isEnable()) {
            return;
        }

        for (final HostConfig hostConfig : ftpConfig.getHosts()) {
            if (Objects.isNull(hostConfig) || BooleanUtils.isFalse(hostConfig.getEnable())) {
                continue;
            }
            taskRegistrar.addCronTask(new DownloadTask(hostConfig), hostConfig.getCron());
        }
    }

}
