package com.github.chencye.app.file2db.config.filter;

import com.github.chencye.app.file2db.config.SuperConfig;

public class LastmodFilterConfig extends SuperConfig {
    private Boolean isLatest;
    private Long millisecond;

    @Override
    public String toString() {
        return "LastmodFilterConfig{" +
                "isLatest=" + isLatest +
                ", millisecond=" + millisecond +
                "} " + super.toString();
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
}
