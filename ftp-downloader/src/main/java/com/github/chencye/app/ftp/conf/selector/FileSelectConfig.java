package com.github.chencye.app.ftp.conf;

import java.util.regex.Pattern;

public class FileSelectConfig {
    private Boolean enable;
    private Pattern fileNameRegex;
    private Boolean enableGetFileInfo;
    private Boolean enableCompareTime;
    private Long millisecond;
    
    private Boolean enableLimitCount;
    private Integer maxFileCount;
    private Boolean enableLimitBatchCount;
    private Integer maxBatchCount;
    
    public Boolean getEnable() {
        return enable;
    }
    
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
    
    public Pattern getFileNameRegex() {
        return fileNameRegex;
    }
    
    public void setFileNameRegex(Pattern fileNameRegex) {
        this.fileNameRegex = fileNameRegex;
    }
    
    public Boolean getEnableGetFileInfo() {
        return enableGetFileInfo;
    }
    
    public void setEnableGetFileInfo(Boolean enableGetFileInfo) {
        this.enableGetFileInfo = enableGetFileInfo;
    }
    
    public Boolean getEnableCompareTime() {
        return enableCompareTime;
    }
    
    public void setEnableCompareTime(Boolean enableCompareTime) {
        this.enableCompareTime = enableCompareTime;
    }
    
    public Long getMillisecond() {
        return millisecond;
    }
    
    public void setMillisecond(Long millisecond) {
        this.millisecond = millisecond;
    }
    
    public Boolean getEnableLimitCount() {
        return enableLimitCount;
    }
    
    public void setEnableLimitCount(Boolean enableLimitCount) {
        this.enableLimitCount = enableLimitCount;
    }
    
    public Integer getMaxFileCount() {
        return maxFileCount;
    }
    
    public void setMaxFileCount(Integer maxFileCount) {
        this.maxFileCount = maxFileCount;
    }
    
    public Boolean getEnableLimitBatchCount() {
        return enableLimitBatchCount;
    }
    
    public void setEnableLimitBatchCount(Boolean enableLimitBatchCount) {
        this.enableLimitBatchCount = enableLimitBatchCount;
    }
    
    public Integer getMaxBatchCount() {
        return maxBatchCount;
    }
    
    public void setMaxBatchCount(Integer maxBatchCount) {
        this.maxBatchCount = maxBatchCount;
    }
    
    @Override
    public String toString() {
        return "FileSelectConfig [enable=" + enable + ", fileNameRegex=" + fileNameRegex + ", enableGetFileInfo=" + enableGetFileInfo
                + ", enableCompareTime=" + enableCompareTime + ", millisecond=" + millisecond + ", enableLimitCount=" + enableLimitCount
                + ", maxFileCount=" + maxFileCount + ", enableLimitBatchCount=" + enableLimitBatchCount + ", maxBatchCount=" + maxBatchCount + "]";
    }
    
}
