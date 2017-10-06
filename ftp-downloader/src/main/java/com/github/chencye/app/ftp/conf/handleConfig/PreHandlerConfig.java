package com.github.chencye.app.ftp.conf.handleConfig;

public class PreHandlerConfig {
    private Boolean enable;

    private Boolean enableCheckExist;
    private Boolean deleteWhenExist;

    private Boolean enableRemoteSuffix;
    private String remoteSuffix;

    private Boolean enableLocalSuffix;
    private String localSuffix;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableCheckExist() {
        return enableCheckExist;
    }

    public void setEnableCheckExist(Boolean enableCheckExist) {
        this.enableCheckExist = enableCheckExist;
    }

    public Boolean getDeleteWhenExist() {
        return deleteWhenExist;
    }

    public void setDeleteWhenExist(Boolean deleteWhenExist) {
        this.deleteWhenExist = deleteWhenExist;
    }

    public Boolean getEnableRemoteSuffix() {
        return enableRemoteSuffix;
    }

    public void setEnableRemoteSuffix(Boolean enableRemoteSuffix) {
        this.enableRemoteSuffix = enableRemoteSuffix;
    }

    public String getRemoteSuffix() {
        return remoteSuffix;
    }

    public void setRemoteSuffix(String remoteSuffix) {
        this.remoteSuffix = remoteSuffix;
    }

    public Boolean getEnableLocalSuffix() {
        return enableLocalSuffix;
    }

    public void setEnableLocalSuffix(Boolean enableLocalSuffix) {
        this.enableLocalSuffix = enableLocalSuffix;
    }

    public String getLocalSuffix() {
        return localSuffix;
    }

    public void setLocalSuffix(String localSuffix) {
        this.localSuffix = localSuffix;
    }

    @Override
    public String toString() {
        return "PreHandlerConfig{" +
                "enable=" + enable +
                ", enableCheckExist=" + enableCheckExist +
                ", deleteWhenExist=" + deleteWhenExist +
                ", enableRemoteSuffix=" + enableRemoteSuffix +
                ", remoteSuffix='" + remoteSuffix + '\'' +
                ", enableLocalSuffix=" + enableLocalSuffix +
                ", localSuffix='" + localSuffix + '\'' +
                '}';
    }
}
