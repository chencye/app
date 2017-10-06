package com.github.chencye.app.ftp.conf;

import com.github.chencye.app.common.ReflectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <pre>
 * FTP配置，注意此类实现了InitializingBean
 * 在初始化完毕后，会将默认值拷贝到各个host配置中
 * </pre>
 * 
 * @author chencye
 * @since 2017.06.11
 */
@Configuration
@ConfigurationProperties("ftp")
public class FTPConfig implements InitializingBean {
    private boolean enable;
    
    private HostConfig defaultConfig;
    
    private List<HostConfig> hosts;
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    
    public HostConfig getDefaultConfig() {
        return defaultConfig;
    }
    
    public void setDefaultConfig(HostConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }
    
    public List<HostConfig> getHosts() {
        return hosts;
    }
    
    public void setHosts(List<HostConfig> hosts) {
        this.hosts = hosts;
    }
    
    @Override
    public String toString() {
        return "FTPConfig [enable=" + enable + ", defaultConfig=" + defaultConfig + ", hosts=" + hosts + "]";
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!enable || defaultConfig == null || CollectionUtils.isEmpty(hosts)) {
            return;
        }
        
        for (HostConfig hostConfig : hosts) {
            ReflectUtils.copyPropertiesOnNull(defaultConfig, hostConfig);
        }
    }
}
