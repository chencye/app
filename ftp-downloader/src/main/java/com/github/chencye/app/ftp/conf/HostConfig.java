package com.github.chencye.app.ftp.conf;

import com.github.chencye.app.ftp.conf.handleConfig.FTPFinalHandlerConfig;
import com.github.chencye.app.ftp.conf.handleConfig.PreHandlerConfig;
import com.github.chencye.app.ftp.conf.handleConfig.SuccessHandlerConfig;

public class HostConfig {
    private Boolean enable;
    private String ip;
    private String username;
    private String password;
    private String remoteDir;
    private String localDir;
    private String type;
    
    private FileSelectConfig fileSelect;
    private PreHandlerConfig preHandler;
    private SuccessHandlerConfig successHandler;
    
    private FTPFinalHandlerConfig finalHandler;
    
    private String cron;
    
    public Boolean getEnable() {
        return enable;
    }
    
    public void setEnable(Boolean enable) {
        this.enable = enable;
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    public SuccessHandlerConfig getSuccessHandler() {
        return successHandler;
    }
    
    public void setSuccessHandler(SuccessHandlerConfig successHandler) {
        this.successHandler = successHandler;
    }
    
    public FTPFinalHandlerConfig getFinalHandler() {
        return finalHandler;
    }
    
    public void setFinalHandler(FTPFinalHandlerConfig finalHandler) {
        this.finalHandler = finalHandler;
    }
    
    public String getCron() {
        return cron;
    }
    
    public void setCron(String cron) {
        this.cron = cron;
    }
    
    @Override
    public String toString() {
        return "HostConfig [enable=" + enable + ", ip=" + ip + ", username=" + username + ", password=" + password + ", remoteDir=" + remoteDir
                + ", localDir=" + localDir + ", type=" + type + ", fileSelect=" + fileSelect + ", preHandler=" + preHandler + ", successHandler="
                + successHandler + ", finalHandler=" + finalHandler + ", cron=" + cron + "]";
    }
    
}
