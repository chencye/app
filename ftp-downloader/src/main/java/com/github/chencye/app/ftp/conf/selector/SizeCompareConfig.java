package com.github.chencye.app.ftp.conf.selector;

public class SizeCompareConfig {
    private Boolean enable;

    private Boolean isSmallest;
    private Long size;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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

    @Override
    public String toString() {
        return "SizeCompareConfig{" +
                "enable=" + enable +
                ", isSmallest=" + isSmallest +
                ", size=" + size +
                '}';
    }
}
