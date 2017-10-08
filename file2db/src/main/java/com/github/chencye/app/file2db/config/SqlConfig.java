package com.github.chencye.app.file2db.config;

import java.nio.charset.Charset;
import java.util.Arrays;

public class SqlConfig extends SuperConfig {
    private String database;
    private String sql;
    private String[] sqlColumns;
    private int[] indexs;

    private Boolean enableCharset;
    private Charset dbCharset;
    private Charset projectCharset;

    @Override
    public String toString() {
        return "SqlConfig{" +
                "database='" + database + '\'' +
                ", sql='" + sql + '\'' +
                ", sqlColumns=" + Arrays.toString(sqlColumns) +
                ", indexs=" + Arrays.toString(indexs) +
                ", enableCharset=" + enableCharset +
                ", dbCharset=" + dbCharset +
                ", projectCharset=" + projectCharset +
                "} " + super.toString();
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
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

    public int[] getIndexs() {
        return indexs;
    }

    public void setIndexs(int[] indexs) {
        this.indexs = indexs;
    }

    public Boolean getEnableCharset() {
        return enableCharset;
    }

    public void setEnableCharset(Boolean enableCharset) {
        this.enableCharset = enableCharset;
    }

    public Charset getDbCharset() {
        return dbCharset;
    }

    public void setDbCharset(Charset dbCharset) {
        this.dbCharset = dbCharset;
    }

    public Charset getProjectCharset() {
        return projectCharset;
    }

    public void setProjectCharset(Charset projectCharset) {
        this.projectCharset = projectCharset;
    }
}
