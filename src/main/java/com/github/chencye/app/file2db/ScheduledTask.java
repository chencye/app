package com.github.chencye.app.file2db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (int i = 0; i < 60000; i++) {
            final int in = i;
            taskRegistrar.addCronTask(() -> {
                System.out.println(in + "thread begin... " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(in + "thread end!!!!! " + Thread.currentThread().getName());
            }, "0/1 * * * * *");
        }
    }

}
