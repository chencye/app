package com.github.chencye.app.file2db.config.handle;

import com.github.chencye.app.file2db.config.SuperConfig;
import com.github.chencye.app.file2db.config.handle.checker.DbCheckerConfig;
import com.github.chencye.app.file2db.config.handle.failed.FailedHandlerConfig;

import java.util.Arrays;
import java.util.regex.Pattern;

public class RowHandlerConfig extends SuperConfig {
    private String name;
    private Pattern rowRegex;
    private String[] rowExts;

    private DbCheckerConfig dbChecker;
    private Row2dbHandlerConfig row2dbHandler;

    private FailedHandlerConfig failedHandler;

    @Override
    public String toString() {
        return "RowHandlerConfig{" +
                "name='" + name + '\'' +
                ", rowRegex=" + rowRegex +
                ", rowExts=" + Arrays.toString(rowExts) +
                ", dbChecker=" + dbChecker +
                ", row2dbHandler=" + row2dbHandler +
                ", failedHandler=" + failedHandler +
                "} " + super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pattern getRowRegex() {
        return rowRegex;
    }

    public void setRowRegex(Pattern rowRegex) {
        this.rowRegex = rowRegex;
    }

    public String[] getRowExts() {
        return rowExts;
    }

    public void setRowExts(String[] rowExts) {
        this.rowExts = rowExts;
    }

    public DbCheckerConfig getDbChecker() {
        return dbChecker;
    }

    public void setDbChecker(DbCheckerConfig dbChecker) {
        this.dbChecker = dbChecker;
    }

    public Row2dbHandlerConfig getRow2dbHandler() {
        return row2dbHandler;
    }

    public void setRow2dbHandler(Row2dbHandlerConfig row2dbHandler) {
        this.row2dbHandler = row2dbHandler;
    }

    public FailedHandlerConfig getFailedHandler() {
        return failedHandler;
    }

    public void setFailedHandler(FailedHandlerConfig failedHandler) {
        this.failedHandler = failedHandler;
    }
}
