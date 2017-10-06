package com.github.chencye.app.ftp.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.chencye.app.ftp.conf.HostConfig;

@SuppressWarnings("unchecked")
public abstract class Handler {
    public static final String FTP_CLIENT = "ftp_client";
    
    public static final String CURRENT_FILE_NAME = "file_name";
    public static final String FILE_NAME_LIST = "file_name_list";
    
    public static final String CURRENT_FILE_SIZE = "file_size";
    public static final String TOTAL_FILE_SIZE = "total_file_size";
    public static final String FILE_SIZE_LIST = "file_size_list";
    
    /** 当前文件最后修改时间，在FTPFileSelector中存入 **/
    public static final String CURRENT_FILE_LASTMODIFIED = "file_lastmodified";
    /** 文件最后修改时间列表，在FTPFileSelector中存入 **/
    public static final String FILE_LASTMODIFIED_LIST = "file_lastmodified_list";
    
    protected final HostConfig hostConfig;
    protected final Map<String, Object> container;
    
    public Handler(HostConfig hostConfig, Map<String, Object> container) {
        this.hostConfig = hostConfig;
        this.container = container;
    }
    
    public <T> T handle(Object... params) throws Exception {
        return  null;
    }
    
    protected void putFilename(String filename) {
        putCurrentFilename(filename);
        putFilenameList(filename);
    }
    
    private void putCurrentFilename(String filename) {
        this.container.put(Handler.CURRENT_FILE_NAME, filename);
    }
    
    private void putFilenameList(String filename) {
        List<String> filenameList = (List<String>) this.container.get(Handler.FILE_NAME_LIST);
        filenameList = filenameList == null ? new ArrayList<String>() : filenameList;
        filenameList.add(filename);
        this.container.put(Handler.FILE_NAME_LIST, filenameList);
    }
    
    protected void putFilesize(long size) {
        putCurrentFileSize(size);
        putTotalFileSize(size);
        putFileSizeList(size);
    }
    
    private void putCurrentFileSize(long size) {
        this.container.put(Handler.CURRENT_FILE_SIZE, size);
    }
    
    private void putTotalFileSize(long size) {
        Long totalSize = (Long) this.container.get(Handler.TOTAL_FILE_SIZE);
        totalSize = totalSize == null ? 0 : totalSize;
        this.container.put(Handler.TOTAL_FILE_SIZE, totalSize);
    }
    
    private void putFileSizeList(long size) {
        List<Long> filesizeList = (List<Long>) this.container.get(Handler.FILE_SIZE_LIST);
        filesizeList = filesizeList == null ? new ArrayList<Long>() : filesizeList;
        filesizeList.add(size);
        this.container.put(Handler.FILE_SIZE_LIST, filesizeList);
    }
    
    public Object get(String key) {
        return this.container.get(key);
    }
    
    public void clear() {
        this.container.clear();
    }
    
}
