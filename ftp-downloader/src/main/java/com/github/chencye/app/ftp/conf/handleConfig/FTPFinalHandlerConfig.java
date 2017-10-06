package com.github.chencye.app.ftp.conf.handleConfig;

import java.nio.charset.Charset;
import java.util.Arrays;

public class FTPFinalHandlerConfig {
    private Boolean enable;
    
    private Boolean enableSQL;
    private String sql;
    private String[] sqlColumns;
    
    private Boolean enableSQLEncoding;
    private Charset[] encodings;
    
    public Boolean getEnable() {
        return enable;
    }
    
    public void setEnable(Boolean enable) {
        this.enable = enable;
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
    
    public Charset[] getEncodings() {
        return encodings;
    }
    
    public void setEncodings(Charset[] encodings) {
        this.encodings = encodings;
    }
    
    @Override
    public String toString() {
        return "FinalHandlerConfig [enable=" + enable + ", enableSQL=" + enableSQL + ", sql=" + sql + ", sqlColumns=" + Arrays.toString(sqlColumns)
                + ", enableSQLEncoding=" + enableSQLEncoding + ", encodings=" + Arrays.toString(encodings) + "]";
    }
    
}
