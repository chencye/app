package com.github.chencye.app.file2db;

import com.github.chencye.app.file2db.config.File2dbConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Starter {
    @Autowired
    File2dbConf file2dbConfig;

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        System.out.println("test---");
        System.out.println("enable: " + file2dbConfig.getEnable());
    }

}
