package com.github.chencye.app.ftp.conf.handleConfig;

public class FTPBeforeHandlerConfig {
    private Boolean enable;
    private Boolean enableCreateLocalDir;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableCreateLocalDir() {
        return enableCreateLocalDir;
    }

    public void setEnableCreateLocalDir(Boolean enableCreateLocalDir) {
        this.enableCreateLocalDir = enableCreateLocalDir;
    }

    @Override
    public String toString() {
        return "FTPBeforeHandlerConfig{" +
                "enable=" + enable +
                ", enableCreateLocalDir=" + enableCreateLocalDir +
                '}';
    }
}
