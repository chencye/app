package com.github.chencye.app.file2db.config.handle;

import com.github.chencye.app.file2db.config.SuperConfig;

public class InitHandlerConfig extends SuperConfig {
    private Boolean enableCreateDir;

    public Boolean getEnableCreateDir() {
        return enableCreateDir;
    }

    public void setEnableCreateDir(Boolean enableCreateDir) {
        this.enableCreateDir = enableCreateDir;
    }

    @Override
    public String toString() {
        return "InitHandlerConfig{" +
                "enableCreateDir=" + enableCreateDir +
                "} " + super.toString();
    }
}
