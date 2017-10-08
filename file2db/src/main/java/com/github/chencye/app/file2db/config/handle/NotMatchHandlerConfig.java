package com.github.chencye.app.file2db.config.handle;

import com.github.chencye.app.file2db.config.SuperConfig;

public class NotMatchHandlerConfig extends SuperConfig {
    private Boolean isMoveOut;
    private String moveOutDir;
    private Boolean delete;

    @Override
    public String toString() {
        return "NotMatchHandlerConfig{" +
                "isMoveOut=" + isMoveOut +
                ", moveOutDir='" + moveOutDir + '\'' +
                ", delete=" + delete +
                "} " + super.toString();
    }

    public Boolean getMoveOut() {
        return isMoveOut;
    }

    public void setMoveOut(Boolean moveOut) {
        isMoveOut = moveOut;
    }

    public String getMoveOutDir() {
        return moveOutDir;
    }

    public void setMoveOutDir(String moveOutDir) {
        this.moveOutDir = moveOutDir;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
