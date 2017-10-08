package com.github.chencye.app.file2db.config;

public abstract class SuperConfig {
    private Boolean enable;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "SuperConfig{" +
                "enable=" + enable +
                '}';
    }
}
