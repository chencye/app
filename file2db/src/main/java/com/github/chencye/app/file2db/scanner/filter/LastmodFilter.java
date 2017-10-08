package com.github.chencye.app.file2db.scanner.filter;

import com.github.chencye.app.file2db.config.filter.LastmodFilterConfig;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * 文件最后修改时间的筛选
 */
@Component
public class LastmodFilter {

    /**
     * 文件最后修改时间的筛选
     *
     * @param lastmodFilterConfig 筛选配置
     * @param attrs               文件属性
     * @return 未开启配置，返回true；未配置时间间隔，返回true；符合规则，返回true。
     */
    public boolean enable(LastmodFilterConfig lastmodFilterConfig, BasicFileAttributes attrs) {
        // 未开启配置，返回通过
        if (Objects.isNull(lastmodFilterConfig) || BooleanUtils.isNotTrue(lastmodFilterConfig.getEnable())) {
            return true;
        }
        // 未配置时间间隔，返回通过
        if (Objects.isNull(lastmodFilterConfig.getMillisecond())) {
            return true;
        }
        long lastModifiedTime = attrs.lastModifiedTime().toMillis();
        long diff = System.currentTimeMillis() - lastModifiedTime;
        return BooleanUtils.isTrue(lastmodFilterConfig.getLatest()) && diff < lastmodFilterConfig.getMillisecond();
    }

}
