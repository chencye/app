package com.github.chencye.app.ftp.conf.handleConfig;

import java.nio.charset.Charset;
import java.util.Arrays;

public class SuccessHandlerConfig {
    private Boolean enable;
    
    private Boolean enableGetFileSize;
    
    private Boolean enableSQL;
    private String sql;
    private String[] sqlColumns;
    
    private Boolean enableSQLEncoding;
    private Charset fromCharset;
    private Charset toCharset;
    
    private Boolean enableBak;
    private String bakDir;
    private Boolean enableDelete;
    
    public Boolean getEnable() {
        return enable;
    }
    
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
    
    public Boolean getEnableGetFileSize() {
        return enableGetFileSize;
    }
    
    public void setEnableGetFileSize(Boolean enableGetFileSize) {
        this.enableGetFileSize = enableGetFileSize;
    }
    
    public Boolean getEnableSQL() {
        return enableSQL;
    }
    
    public void setEnableSQL(Boolean enableSQL) {
        this.enableSQL = enableSQL;
    }
    
    public String getSql() {
        return sql;
    }
    
    public void setSql(String sql) {
        this.sql = sql;
    }
    
    public String[] getSqlColumns() {
        return sqlColumns;
    }
    
    public void setSqlColumns(String[] sqlColumns) {
        this.sqlColumns = sqlColumns;
    }
    
    public Boolean getEnableSQLEncoding() {
        return enableSQLEncoding;
    }
    
    public void setEnableSQLEncoding(Boolean enableSQLEncoding) {
        this.enableSQLEncoding = enableSQLEncoding;
    }
    
    public Charset getFromCharset() {
        return fromCharset;
    }
    
    public void setFromCharset(Charset fromCharset) {
        this.fromCharset = fromCharset;
    }
    
    public Charset getToCharset() {
        return toCharset;
    }
    
    public void setToCharset(Charset toCharset) {
        this.toCharset = toCharset;
    }
    
    public Boolean getEnableBak() {
        return enableBak;
    }
    
    public void setEnableBak(Boolean enableBak) {
        this.enableBak = enableBak;
    }
    
    public String getBakDir() {
        return bakDir;
    }
    
    public void setBakDir(String bakDir) {
        this.bakDir = bakDir;
    }
    
    public Boolean getEnableDelete() {
        return enableDelete;
    }
    
    public void setEnableDelete(Boolean enableDelete) {
        this.enableDelete = enableDelete;
    }
    
    @Override
    public String toString() {
        return "SuccessHandlerConfig [enable=" + enable + ", enableGetFileSize=" + enableGetFileSize + ", enableSQL=" + enableSQL + ", sql=" + sql
                + ", sqlColumns=" + Arrays.toString(sqlColumns) + ", enableSQLEncoding=" + enableSQLEncoding + ", fromCharset=" + fromCharset
                + ", toCharset=" + toCharset + ", enableBak=" + enableBak + ", bakDir=" + bakDir + ", enableDelete=" + enableDelete + "]";
    }
    
}
