package com.github.chencye.app.ftp.conf.handleConfig;

public class FTPBeforeHandlerConfig {
    private Boolean enable;
    
    public Boolean getEnable() {
        return enable;
    }
    
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
    
    @Override
    public String toString() {
        return "BeforeHandlerConfig [enable=" + enable + "]";
    }
    
}
