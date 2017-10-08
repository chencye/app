package com.github.chencye.app.file2db.config;

import com.github.chencye.app.file2db.util.ReflectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
@ConfigurationProperties("file2db")
public class File2dbConfig implements InitializingBean {
    private boolean enable;
    private PathConfig defaultConfig;
    private List<PathConfig> paths;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public PathConfig getDefaultConfig() {
        return defaultConfig;
    }

    public void setDefaultConfig(PathConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    public List<PathConfig> getPaths() {
        return paths;
    }

    public void setPaths(List<PathConfig> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "File2dbConfig{" +
                "enable=" + enable +
                ", defaultConfig=" + defaultConfig +
                ", paths=" + paths +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!enable || defaultConfig == null || CollectionUtils.isEmpty(paths)) {
            return;
        }
        for (PathConfig pathConfig : paths) {
            ReflectUtils.copyPropertiesOnNull(defaultConfig, pathConfig);
        }
    }
}
