package com.github.chencye.app.ftp.constant;

public class Constant {
    public final static String TYPE_UPLOAD = "upload";
    public final static String TYPE_DOWNLOAD = "download";
    
    public final static String LOG_MDC_COMMON = "common";
    
    /** 为便于识别正在下载的文件，避免被误删除，下载中的文件，添加此后缀；下载完毕后，再修正 **/
    public final static String DOWNLOADING_FILENAME_SUFFIX = "_downloading";
}
