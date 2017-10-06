package com.github.chencye.app.ftp.conf;

import com.github.chencye.app.ftp.conf.handleConfig.*;
import com.github.chencye.app.ftp.conf.selector.FileSelectConfig;

public class HostConfig {
    private Boolean enable;

    private String cron;

    private String ip;
    private String username;
    private String password;
    private String remoteDir;
    private String localDir;

    private FTPBeforeHandlerConfig beforeHandler;

    private FileSelectConfig fileSelect;
    private PreHandlerConfig preHandler;
    private RemoteHandlerConfig remoteHandler;
    private SuccessHandlerConfig successHandler;
    private FailedHandlerConfig failedHandler;

    private FTPFinalHandlerConfig finalHandler;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public void setRemoteDir(String remoteDir) {
        this.remoteDir = remoteDir;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public FTPBeforeHandlerConfig getBeforeHandler() {
        return beforeHandler;
    }

    public void setBeforeHandler(FTPBeforeHandlerConfig beforeHandler) {
        this.beforeHandler = beforeHandler;
    }

    public FileSelectConfig getFileSelect() {
        return fileSelect;
    }

    public void setFileSelect(FileSelectConfig fileSelect) {
        this.fileSelect = fileSelect;
    }

    public PreHandlerConfig getPreHandler() {
        return preHandler;
    }

    public void setPreHandler(PreHandlerConfig preHandler) {
        this.preHandler = preHandler;
    }

    public RemoteHandlerConfig getRemoteHandler() {
        return remoteHandler;
    }

    public void setRemoteHandler(RemoteHandlerConfig remoteHandler) {
        this.remoteHandler = remoteHandler;
    }

    public SuccessHandlerConfig getSuccessHandler() {
        return successHandler;
    }

    public void setSuccessHandler(SuccessHandlerConfig successHandler) {
        this.successHandler = successHandler;
    }

    public FailedHandlerConfig getFailedHandler() {
        return failedHandler;
    }

    public void setFailedHandler(FailedHandlerConfig failedHandler) {
        this.failedHandler = failedHandler;
    }

    public FTPFinalHandlerConfig getFinalHandler() {
        return finalHandler;
    }

    public void setFinalHandler(FTPFinalHandlerConfig finalHandler) {
        this.finalHandler = finalHandler;
    }

    @Override
    public String toString() {
        return "HostConfig{" +
                "enable=" + enable +
                ", cron='" + cron + '\'' +
                ", ip='" + ip + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", remoteDir='" + remoteDir + '\'' +
                ", localDir='" + localDir + '\'' +
                ", beforeHandler=" + beforeHandler +
                ", fileSelect=" + fileSelect +
                ", preHandler=" + preHandler +
                ", remoteHandler=" + remoteHandler +
                ", successHandler=" + successHandler +
                ", failedHandler=" + failedHandler +
                ", finalHandler=" + finalHandler +
                '}';
    }
}
