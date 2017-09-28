package com.github.chencye.app.file2db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
