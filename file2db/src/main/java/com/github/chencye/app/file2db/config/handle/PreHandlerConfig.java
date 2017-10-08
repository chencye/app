package com.github.chencye.app.file2db.config.handle;

import com.github.chencye.app.file2db.config.SuperConfig;

import java.util.Arrays;

public class PreHandlerConfig extends SuperConfig {
    private Boolean enableAddMark;
    private String mark;
    private Boolean enableLog;
    private String logSQL;
    private String[] logSQLColumns;

    public Boolean getEnableAddMark() {
        return enableAddMark;
    }

    public void setEnableAddMark(Boolean enableAddMark) {
        this.enableAddMark = enableAddMark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Boolean getEnableLog() {
        return enableLog;
    }

    public void setEnableLog(Boolean enableLog) {
        this.enableLog = enableLog;
    }

    public String getLogSQL() {
        return logSQL;
    }

    public void setLogSQL(String logSQL) {
        this.logSQL = logSQL;
    }

    public String[] getLogSQLColumns() {
        return logSQLColumns;
    }

    public void setLogSQLColumns(String[] logSQLColumns) {
        this.logSQLColumns = logSQLColumns;
    }

    @Override
    public String toString() {
        return "PreHandlerConfig{" +
                "enableAddMark=" + enableAddMark +
                ", mark='" + mark + '\'' +
                ", enableLog=" + enableLog +
                ", logSQL='" + logSQL + '\'' +
                ", logSQLColumns=" + Arrays.toString(logSQLColumns) +
                "} " + super.toString();
    }
}
