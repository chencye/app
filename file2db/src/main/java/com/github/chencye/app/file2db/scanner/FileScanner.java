package com.github.chencye.app.file2db.scanner;

import com.github.chencye.app.file2db.config.FileScannerConfig;
import com.github.chencye.app.file2db.config.PathConfig;
import com.github.chencye.app.file2db.handler.NotMatchHandler;
import com.github.chencye.app.file2db.scanner.filter.DbFilter;
import com.github.chencye.app.file2db.scanner.filter.LastmodFilter;
import com.github.chencye.app.file2db.scanner.filter.NameFilter;
import com.github.chencye.app.file2db.scanner.filter.SizeFilter;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/**
 * 获取并筛选文件
 */
@Component
public class FileScanner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NameFilter nameFilter;
    @Autowired
    private LastmodFilter lastmodFilter;
    @Autowired
    private SizeFilter sizeFilter;
    @Autowired
    private DbFilter dbFilter;

    @Autowired
    private NotMatchHandler notMatchHandler;

    /**
     * 获取并筛选文件
     *
     * @param pathConfig 配置
     * @return 筛选后的文件
     */
    public List<Path> scan(PathConfig pathConfig) {
        Path path = Paths.get(pathConfig.getPath());
        // 扫描路径非文件夹，返回空集
        if (!Files.isDirectory(path)) {
            return new ArrayList<>();
        }
        FileScannerConfig fileScannerConfig = pathConfig.getFileScanner();
        // 未开启文件筛选，则返回文件夹下所有文件
        if (Objects.isNull(fileScannerConfig) || BooleanUtils.isNotTrue(fileScannerConfig.getEnable())) {
            return scan(path);
        }
        // 根据配置，筛选文件
        return scan(fileScannerConfig, path);
    }

    /**
     * 扫描并返回文件夹下所有文件
     *
     * @param path 扫描文件夹
     * @return 扫描到的所有文件
     */
    private List<Path> scan(Path path) {
        final List<Path> files = new ArrayList<>();
        try {
            // 只扫描一层，不扫描子文件夹下的文件
            Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), 1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 文件夹跳过
                    if (Files.isDirectory(file)) {
                        return FileVisitResult.CONTINUE;
                    }
                    files.add(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.info("getAllFiles error.", e);
        }
        return files;
    }

    /**
     * 根据配置，筛选文件
     *
     * @param fileScannerConfig 筛选配置
     * @param path              扫描的文件夹
     * @return 筛选出来的文件
     */
    private List<Path> scan(final FileScannerConfig fileScannerConfig, final Path path) {
        final List<Path> files = new ArrayList<>();
        try {
            // 只扫描一层，不扫描子文件夹下的文件
            Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), 1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    logger.debug("scanner: file={}", file.getFileName().toString());
                    // 文件夹跳过
                    if (Files.isDirectory(file)) {
                        return FileVisitResult.CONTINUE;
                    }
                    // 文件名不符合规则的跳过
                    // 最后修改时间不符合的跳过
                    // 文件大小不符合的跳过
                    // 库表检查已存在的跳过
                    if (!nameFilter.enable(fileScannerConfig.getNameFilter(), file)
                            || !lastmodFilter.enable(fileScannerConfig.getLastmodFilter(), attrs)
                            || !sizeFilter.enable(fileScannerConfig.getSizeFilter(), attrs)
                            || !dbFilter.enable(fileScannerConfig.getDbFilter(), file)) {
                        notMatchHandler.handle(fileScannerConfig.getNotMatchHandler(), file);
                        return FileVisitResult.CONTINUE;
                    }
                    // 符合规则，添加到列表
                    files.add(file);
                    // 判断已获取文件数量是否达到限制，若达到限制，则中断
                    if (isOverMaxCount(fileScannerConfig, files.size())) {
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.info("scanner error.", e);
        }
        return files;
    }

    /**
     * 判断已获取文件数量是否达到限制
     *
     * @param fileScannerConfig 筛选配置
     * @param count             当前获取文件数量
     * @return 达到限制时返回true
     */
    private boolean isOverMaxCount(FileScannerConfig fileScannerConfig, int count) {
        return Objects.nonNull(fileScannerConfig.getMaxCount()) && count >= fileScannerConfig.getMaxCount();
    }

}
