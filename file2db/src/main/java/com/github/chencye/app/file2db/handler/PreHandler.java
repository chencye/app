package com.github.chencye.app.file2db.handler;

import com.github.chencye.app.file2db.config.PathConfig;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class PreHandler {

    public Path handle(PathConfig pathConfig, Path path) {
        return path;
    }

}
