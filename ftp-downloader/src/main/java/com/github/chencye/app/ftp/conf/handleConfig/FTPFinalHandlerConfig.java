package com.github.chencye.app.ftp.conf.handleConfig;

public class FTPFinalHandlerConfig {
    private Boolean enable;
    private Boolean enableCloseFTP;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableCloseFTP() {
        return enableCloseFTP;
    }

    public void setEnableCloseFTP(Boolean enableCloseFTP) {
        this.enableCloseFTP = enableCloseFTP;
    }

    @Override
    public String toString() {
        return "FTPFinalHandlerConfig{" +
                "enable=" + enable +
                ", enableCloseFTP=" + enableCloseFTP +
                '}';
    }
}
