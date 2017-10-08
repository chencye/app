package com.github.chencye.app.file2db.scanner.filter;

import com.github.chencye.app.file2db.config.filter.SizeFilterConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * 文件大小的筛选
 */
@Component
public class SizeFilter {

    /**
     * 文件大小的筛选
     *
     * @param sizeFilterConfig 筛选配置
     * @param attrs            文件属性
     * @return 未开启配置，返回true；未配置文件大小限制，返回true；符合规则，返回true。
     */
    public boolean enable(SizeFilterConfig sizeFilterConfig, BasicFileAttributes attrs) {
        // 未开启配置，返回通过
        if (Objects.isNull(sizeFilterConfig) || BooleanUtils.isNotTrue(sizeFilterConfig.getEnable())) {
            return true;
        }
        // 未配置时间间隔，返回通过
        if (Objects.isNull(sizeFilterConfig.getSize())) {
            return true;
        }
        return BooleanUtils.isTrue(sizeFilterConfig.getSmallest()) && attrs.size() < sizeFilterConfig.getSize();
    }
}
