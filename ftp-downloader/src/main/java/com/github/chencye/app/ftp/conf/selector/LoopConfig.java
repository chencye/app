package com.github.chencye.app.ftp.conf.selector;

public class LoopConfig {
    private Boolean enable;
    private Boolean isLimitLoopCount;
    private Integer maxLoop;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getLimitLoopCount() {
        return isLimitLoopCount;
    }

    public void setLimitLoopCount(Boolean limitLoopCount) {
        isLimitLoopCount = limitLoopCount;
    }

    public Integer getMaxLoop() {
        return maxLoop;
    }

    public void setMaxLoop(Integer maxLoop) {
        this.maxLoop = maxLoop;
    }

    @Override
    public String toString() {
        return "LoopConfig{" +
                "enable=" + enable +
                ", isLimitLoopCount=" + isLimitLoopCount +
                ", maxLoop=" + maxLoop +
                '}';
    }
}
