package com.github.chencye.app.ftp.conf.selector;

import java.util.regex.Pattern;

public class FileSelectConfig {
    private Boolean enable;

    private Integer maxCount;

    private Pattern fileNameRegex;
    private TimeCompareConfig timeCompare;
    private SizeCompareConfig sizeCompare;

    private LoopConfig loop;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Pattern getFileNameRegex() {
        return fileNameRegex;
    }

    public void setFileNameRegex(Pattern fileNameRegex) {
        this.fileNameRegex = fileNameRegex;
    }

    public TimeCompareConfig getTimeCompare() {
        return timeCompare;
    }

    public void setTimeCompare(TimeCompareConfig timeCompare) {
        this.timeCompare = timeCompare;
    }

    public SizeCompareConfig getSizeCompare() {
        return sizeCompare;
    }

    public void setSizeCompare(SizeCompareConfig sizeCompare) {
        this.sizeCompare = sizeCompare;
    }

    public LoopConfig getLoop() {
        return loop;
    }

    public void setLoop(LoopConfig loop) {
        this.loop = loop;
    }

    @Override
    public String toString() {
        return "FileSelectConfig{" +
                "enable=" + enable +
                ", maxCount=" + maxCount +
                ", fileNameRegex=" + fileNameRegex +
                ", timeCompare=" + timeCompare +
                ", sizeCompare=" + sizeCompare +
                ", loop=" + loop +
                '}';
    }
}
