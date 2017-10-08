package com.github.chencye.app.file2db.scanner.filter;

import com.github.chencye.app.file2db.config.filter.DbFilterConfig;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class DbFilter {

    public boolean enable(DbFilterConfig dbFilterConfig, Path path) {
        return true;
    }

}
