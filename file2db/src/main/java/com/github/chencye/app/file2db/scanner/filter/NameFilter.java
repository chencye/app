package com.github.chencye.app.file2db.scanner.filter;

import com.github.chencye.app.file2db.config.filter.NameFilterConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Objects;

/**
 * 文件名筛选
 */
@Component
public class NameFilter {

    /**
     * 文件名筛选
     *
     * @param nameFilterConfig 筛选配置
     * @param path             文件
     * @return 未开启配置，返回true；未配置正则，返回true；正则匹配，返回true。
     */
    public boolean enable(NameFilterConfig nameFilterConfig, Path path) {
        // 未开启配置，返回通过
        // 未配置正则，返回通过
        // 正则匹配，返回通过
        return Objects.isNull(nameFilterConfig) || BooleanUtils.isNotTrue(nameFilterConfig.getEnable()) || Objects.isNull(nameFilterConfig.getRegex
                ()) || nameFilterConfig.getRegex().matcher(path.getFileName().toString()).matches();
    }

}
