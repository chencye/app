package com.github.chencye.app.ftp.conf.selector;

public class TimeCompareConfig {
    private Boolean enable;

    private Boolean isLatest;
    private Long millisecond;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getLatest() {
        return isLatest;
    }

    public void setLatest(Boolean latest) {
        isLatest = latest;
    }

    public Long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(Long millisecond) {
        this.millisecond = millisecond;
    }

    @Override
    public String toString() {
        return "TimeCompareConfig{" +
                "enable=" + enable +
                ", isLatest=" + isLatest +
                ", millisecond=" + millisecond +
                '}';
    }
}
