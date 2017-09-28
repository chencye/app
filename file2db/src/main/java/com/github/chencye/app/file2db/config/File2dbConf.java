package com.github.chencye.app.file2db.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("file2db")
public class File2dbConf extends FileHandleConf {
    private PathConf defaultPath;
    private List<PathConf> paths;


}
