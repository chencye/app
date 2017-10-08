package com.github.chencye.app.file2db.config;

import com.github.chencye.app.file2db.config.filter.*;
import com.github.chencye.app.file2db.config.handle.NotMatchHandlerConfig;

public class FileScannerConfig extends SuperConfig {
    private Integer maxCount;
    private NameFilterConfig nameFilter;
    private LastmodFilterConfig lastmodFilter;
    private SizeFilterConfig sizeFilter;
    private DbFilterConfig dbFilter;
    private NotMatchHandlerConfig notMatchHandler;

    @Override
    public String toString() {
        return "FileScannerConfig{" +
                "maxCount=" + maxCount +
                ", nameFilter=" + nameFilter +
                ", lastmodFilter=" + lastmodFilter +
                ", sizeFilter=" + sizeFilter +
                ", dbFilter=" + dbFilter +
                ", notMatchHandler=" + notMatchHandler +
                "} " + super.toString();
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public NameFilterConfig getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(NameFilterConfig nameFilter) {
        this.nameFilter = nameFilter;
    }

    public LastmodFilterConfig getLastmodFilter() {
        return lastmodFilter;
    }

    public void setLastmodFilter(LastmodFilterConfig lastmodFilter) {
        this.lastmodFilter = lastmodFilter;
    }

    public SizeFilterConfig getSizeFilter() {
        return sizeFilter;
    }

    public void setSizeFilter(SizeFilterConfig sizeFilter) {
        this.sizeFilter = sizeFilter;
    }

    public DbFilterConfig getDbFilter() {
        return dbFilter;
    }

    public void setDbFilter(DbFilterConfig dbFilter) {
        this.dbFilter = dbFilter;
    }

    public NotMatchHandlerConfig getNotMatchHandler() {
        return notMatchHandler;
    }

    public void setNotMatchHandler(NotMatchHandlerConfig notMatchHandler) {
        this.notMatchHandler = notMatchHandler;
    }
}
