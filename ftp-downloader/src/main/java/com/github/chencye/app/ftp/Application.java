package com.github.chencye.app.ftp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <pre>
 * FTP操作启动类
 * 使用spring scheduling进行定时操作
 * </pre>
 * 
 * @see com.github.chencye.app.ftp.task.ScheduledTask
 * @author chencye 2017-06-30 22:49:03
 */
@SpringBootApplication(scanBasePackages = "com.github.chencye")
@EnableScheduling
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /**
     * spring scheduling的线程池配置
     * 
     * @param size
     *            线程池大小
     * @return
     */
    @Bean
    public Executor taskExecutor(@Value("${spring.taskExecutor.poolSize}") int size) {
        return Executors.newScheduledThreadPool(size);
    }
}
