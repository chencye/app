package com.github.chencye.app.file2db.config.handle.failed;

import com.github.chencye.app.file2db.config.SuperConfig;

public class FailedHandlerConfig extends SuperConfig {
    private ToFileHandlerConfig toFileHandler;
    private ToDbHandlerConfig toDbHandler;

    @Override
    public String toString() {
        return "FailedHandlerConfig{" +
                "toFileHandler=" + toFileHandler +
                ", toDbHandler=" + toDbHandler +
                "} " + super.toString();
    }

    public ToFileHandlerConfig getToFileHandler() {
        return toFileHandler;
    }

    public void setToFileHandler(ToFileHandlerConfig toFileHandler) {
        this.toFileHandler = toFileHandler;
    }

    public ToDbHandlerConfig getToDbHandler() {
        return toDbHandler;
    }

    public void setToDbHandler(ToDbHandlerConfig toDbHandler) {
        this.toDbHandler = toDbHandler;
    }
}
