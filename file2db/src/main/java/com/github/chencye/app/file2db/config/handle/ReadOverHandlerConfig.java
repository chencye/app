package com.github.chencye.app.file2db.config.handle;

import com.github.chencye.app.file2db.config.SuperConfig;

public class ReadOverHandlerConfig extends SuperConfig {
    private Boolean isBak;
    private String bakDir;
    private Boolean delete;

    @Override
    public String toString() {
        return "ReadOverHandlerConfig{" +
                "isBak=" + isBak +
                ", bakDir='" + bakDir + '\'' +
                ", delete=" + delete +
                "} " + super.toString();
    }

    public Boolean getBak() {
        return isBak;
    }

    public void setBak(Boolean bak) {
        isBak = bak;
    }

    public String getBakDir() {
        return bakDir;
    }

    public void setBakDir(String bakDir) {
        this.bakDir = bakDir;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
