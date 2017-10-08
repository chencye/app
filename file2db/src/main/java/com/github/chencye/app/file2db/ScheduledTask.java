package com.github.chencye.app.file2db;

import com.github.chencye.app.file2db.config.File2dbConfig;
import com.github.chencye.app.file2db.config.PathConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * spring scheduling 定时执行任务
 * 线程池，在com.github.chencye.app.file2db.Application中配置
 * </pre>
 */
@Component
public class ScheduledTask implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private File2dbConfig file2dbConfig;
    private File2dbAction file2dbAction;

    @Autowired
    public ScheduledTask(File2dbConfig file2dbConfig, File2dbAction file2dbAction) {
        this.file2dbConfig = file2dbConfig;
        this.file2dbAction = file2dbAction;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (Objects.isNull(file2dbConfig) || !file2dbConfig.isEnable()) {
            logger.info("disable to execute.");
            return;
        }
        List<PathConfig> pathConfigs = file2dbConfig.getPaths();
        if (CollectionUtils.isEmpty(pathConfigs)) {
            logger.info("empty config.");
            return;
        }
        List<PathConfig> enablePathConfigs = new ArrayList<>(pathConfigs.size());
        for (PathConfig pathConfig : pathConfigs) {
            if (Objects.isNull(pathConfig) || BooleanUtils.isNotTrue(pathConfig.getEnable())) {
                logger.info("disable path[{}]", Objects.isNull(pathConfig) ? "" : pathConfig.getPath());
                continue;
            }
            enablePathConfigs.add(pathConfig);
        }
        if (enablePathConfigs.isEmpty()) {
            logger.info("no enable path to execute.");
            return;
        }
        for (PathConfig pathConfig : enablePathConfigs) {
            logger.info("add enable task: path={}", pathConfig.getPath());
            // taskRegistrar.addCronTask(() -> file2dbAction.action(pathConfig), pathConfig.getCron());
            taskRegistrar.addCronTask(() -> file2dbAction.action(pathConfig), pathConfig.getCron());
        }

    }

}
