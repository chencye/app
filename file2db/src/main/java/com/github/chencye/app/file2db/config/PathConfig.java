package com.github.chencye.app.file2db.config;

import com.github.chencye.app.file2db.config.handle.*;

import java.nio.charset.Charset;

public class PathConfig extends SuperConfig {
    private String cron;
    private String path;
    private Charset encoding;

    private InitHandlerConfig initHandler;
    private FileScannerConfig fileScanner;
    private PreHandlerConfig preHandler;
    private RowHandlerConfig rowHandler;
    private LogHandlerConfig logHandler;
    private ReadOverHandlerConfig readOverHandler;
    private FinalHandlerConfig finalHandler;

    @Override
    public String toString() {
        return "PathConfig{" +
                "cron='" + cron + '\'' +
                ", path='" + path + '\'' +
                ", encoding=" + encoding +
                ", initHandler=" + initHandler +
                ", fileScanner=" + fileScanner +
                ", preHandler=" + preHandler +
                ", rowHandler=" + rowHandler +
                ", logHandler=" + logHandler +
                ", readOverHandler=" + readOverHandler +
                ", finalHandler=" + finalHandler +
                "} " + super.toString();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Charset getEncoding() {
        return encoding;
    }

    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }

    public InitHandlerConfig getInitHandler() {
        return initHandler;
    }

    public void setInitHandler(InitHandlerConfig initHandler) {
        this.initHandler = initHandler;
    }

    public FileScannerConfig getFileScanner() {
        return fileScanner;
    }

    public void setFileScanner(FileScannerConfig fileScanner) {
        this.fileScanner = fileScanner;
    }

    public PreHandlerConfig getPreHandler() {
        return preHandler;
    }

    public void setPreHandler(PreHandlerConfig preHandler) {
        this.preHandler = preHandler;
    }

    public RowHandlerConfig getRowHandler() {
        return rowHandler;
    }

    public void setRowHandler(RowHandlerConfig rowHandler) {
        this.rowHandler = rowHandler;
    }

    public LogHandlerConfig getLogHandler() {
        return logHandler;
    }

    public void setLogHandler(LogHandlerConfig logHandler) {
        this.logHandler = logHandler;
    }

    public ReadOverHandlerConfig getReadOverHandler() {
        return readOverHandler;
    }

    public void setReadOverHandler(ReadOverHandlerConfig readOverHandler) {
        this.readOverHandler = readOverHandler;
    }

    public FinalHandlerConfig getFinalHandler() {
        return finalHandler;
    }

    public void setFinalHandler(FinalHandlerConfig finalHandler) {
        this.finalHandler = finalHandler;
    }
}
