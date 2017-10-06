package com.github.chencye.app.ftp.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.enterprisedt.net.ftp.FTPClient;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.handler.Handler;

/**
 * <pre>
 * 文件选择器
 * 提供符合要求，可以进行FTP操作的文件名
 * </pre>
 * 
 * @author chencye 2017-06-12 20:59:19
 */
@SuppressWarnings("unchecked")
public abstract class FTPFileSelector {
    
    protected final HostConfig hostConfig;
    protected final Map<String, Object> container;
    
    public FTPFileSelector(HostConfig hostConfig, Map<String, Object> container) {
        this.hostConfig = hostConfig;
        this.container = container;
    }
    
    public FTPFileSelector() {
        this.hostConfig = null;
        this.container = null;
    }
    
    public abstract List<String> select(Object... params) throws Exception;
    
    protected void putLastModified(long lastModified) {
        putCurrentLastModified(lastModified);
        putLastModifiedList(lastModified);
    }
    
    private void putCurrentLastModified(long lastModified) {
        this.container.put(Handler.CURRENT_FILE_LASTMODIFIED, lastModified);
    }
    
    private void putLastModifiedList(long lastModified) {
        List<Long> lastModifiedList = (List<Long>) this.container.get(Handler.FILE_LASTMODIFIED_LIST);
        lastModifiedList = lastModifiedList == null ? new ArrayList<Long>() : lastModifiedList;
        lastModifiedList.add(lastModified);
        this.container.put(Handler.FILE_LASTMODIFIED_LIST, lastModifiedList);
    }
    
    public void putFTPClient(FTPClient ftpClient) {
        this.container.put(Handler.FTP_CLIENT, ftpClient);
    }
}
