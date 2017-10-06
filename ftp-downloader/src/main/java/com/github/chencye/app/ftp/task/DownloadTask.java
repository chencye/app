package com.github.chencye.app.ftp.task;

import com.github.chencye.app.ftp.FTP;
import com.github.chencye.app.ftp.conf.HostConfig;
import com.github.chencye.app.ftp.constant.Constant;
import com.github.chencye.app.ftp.handler.Handler;
import com.github.chencye.app.ftp.handler.HandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class DownloadTask implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected final HostConfig hostConfig;

    public DownloadTask(HostConfig hostConfig) {
        this.hostConfig = hostConfig;
    }

    @Override
    public void run() {
        // 设置日志打印公用信息
        MDC.put(Constant.LOG_MDC_COMMON, hostConfig.getIp());
        action();
        // 去掉日志打印公用信息
        MDC.remove(Constant.LOG_MDC_COMMON);
    }

    private void action() {
        FTP ftp = null;
        try {
            Handler beforeHandler = HandlerFactory.getBeforeHandler(hostConfig);
            beforeHandler.handle();

            ftp = new FTP(hostConfig);
            logger.info("begin download.");
            ftp.download(hostConfig.getRemoteDir(), hostConfig.getLocalDir());
            logger.info("end download.");
        } catch (Exception e) {
            logger.error("download error.", e);
        } finally {
            Handler finalHandler = HandlerFactory.getFinalHandler(hostConfig);
            try {
                finalHandler.handle(ftp);
            } catch (Exception e) {
                logger.error("finalHandler error.", e);
            }
        }
    }
}
