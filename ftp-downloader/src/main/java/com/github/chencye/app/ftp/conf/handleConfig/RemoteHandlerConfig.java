package com.github.chencye.app.ftp.conf.handleConfig;

public class RemoteHandlerConfig {
    private Boolean enable;
    private Boolean enableFixedFilename;
    private Boolean enableBak;
    private String bakDir;
    private Boolean enableDelete;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableFixedFilename() {
        return enableFixedFilename;
    }

    public void setEnableFixedFilename(Boolean enableFixedFilename) {
        this.enableFixedFilename = enableFixedFilename;
    }

    public Boolean getEnableBak() {
        return enableBak;
    }

    public void setEnableBak(Boolean enableBak) {
        this.enableBak = enableBak;
    }

    public String getBakDir() {
        return bakDir;
    }

    public void setBakDir(String bakDir) {
        this.bakDir = bakDir;
    }

    public Boolean getEnableDelete() {
        return enableDelete;
    }

    public void setEnableDelete(Boolean enableDelete) {
        this.enableDelete = enableDelete;
    }

    @Override
    public String toString() {
        return "RemoteHandlerConfig{" +
                "enable=" + enable +
                ", enableFixedFilename=" + enableFixedFilename +
                ", enableBak=" + enableBak +
                ", bakDir='" + bakDir + '\'' +
                ", enableDelete=" + enableDelete +
                '}';
    }
}
