package com.github.chencye.app.file2db.config.filter;

import com.github.chencye.app.file2db.config.SuperConfig;

public class SizeFilterConfig extends SuperConfig {
    private Boolean isSmallest;
    private Long size;

    @Override
    public String toString() {
        return "SizeFilterConfig{" +
                "isSmallest=" + isSmallest +
                ", size=" + size +
                "} " + super.toString();
    }

    public Boolean getSmallest() {
        return isSmallest;
    }

    public void setSmallest(Boolean smallest) {
        isSmallest = smallest;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
