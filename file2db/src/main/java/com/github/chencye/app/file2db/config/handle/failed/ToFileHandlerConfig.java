package com.github.chencye.app.file2db.config.handle.failed;

import com.github.chencye.app.file2db.config.SuperConfig;

public class ToFileHandlerConfig extends SuperConfig {
    private String path;
    private String filenameSuffix;
    private Boolean onlyRowContent;
    private Boolean saveLineNumber;
    private Boolean saveReason;

    @Override
    public String toString() {
        return "ToFileHandlerConfig{" +
                "path='" + path + '\'' +
                ", filenameSuffix='" + filenameSuffix + '\'' +
                ", onlyRowContent=" + onlyRowContent +
                ", saveLineNumber=" + saveLineNumber +
                ", saveReason=" + saveReason +
                "} " + super.toString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilenameSuffix() {
        return filenameSuffix;
    }

    public void setFilenameSuffix(String filenameSuffix) {
        this.filenameSuffix = filenameSuffix;
    }

    public Boolean getOnlyRowContent() {
        return onlyRowContent;
    }

    public void setOnlyRowContent(Boolean onlyRowContent) {
        this.onlyRowContent = onlyRowContent;
    }

    public Boolean getSaveLineNumber() {
        return saveLineNumber;
    }

    public void setSaveLineNumber(Boolean saveLineNumber) {
        this.saveLineNumber = saveLineNumber;
    }

    public Boolean getSaveReason() {
        return saveReason;
    }

    public void setSaveReason(Boolean saveReason) {
        this.saveReason = saveReason;
    }
}
