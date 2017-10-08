package com.github.chencye.app.file2db;

import com.github.chencye.app.file2db.config.PathConfig;
import com.github.chencye.app.file2db.handler.*;
import com.github.chencye.app.file2db.scanner.FileScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.util.List;

/**
 * <pre>
 * 入库启动入口
 * 由spring定时任务驱动
 * </pre>
 *
 * @see ScheduledTask
 */
@Component
public class File2dbAction {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InitHandler initHandler;
    @Autowired
    private FileScanner fileScanner;
    @Autowired
    private PreHandler preHandler;
    @Autowired
    private RowHandler rowHandler;
    @Autowired
    private LogHandler logHandler;
    @Autowired
    private ReadOverHandler readOverHandler;
    @Autowired
    private FinalHandler finalHandler;

    void action(PathConfig pathConfig) {
        logger.info("begin action...");
        // 初始化，如创建备份文件夹
        initHandler.init(pathConfig);
        while (true) {
            // 扫描并筛选文件
            List<Path> files = fileScanner.scan(pathConfig);
            // 未扫描到文件则结束
            if (CollectionUtils.isEmpty(files)) {
                logger.info("stop. no file scan.");
                break;
            }
            // 遍历筛选出来的文件
            for (Path path : files) {
                // 读取文件之前的操作，如添加文件名后缀，写日志
                path = preHandler.handle(pathConfig, path);
                // 读取文件，并进行入库操作
                rowHandler.handle(pathConfig, path);
                // 读取完毕后添加或更新日志
                logHandler.handle(pathConfig, path);
                // 读取完毕后，备份或删除文件
                readOverHandler.handle(pathConfig, path);
            }
        }
        // 结束前后处理，如释放连接等
        finalHandler.handle(pathConfig);
        logger.info("end action.");
    }

}
