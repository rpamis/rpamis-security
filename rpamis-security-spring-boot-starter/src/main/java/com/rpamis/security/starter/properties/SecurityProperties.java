package com.rpamis.security.starter.properties;

import com.rpamis.security.core.properties.Algorithm;
import com.rpamis.security.core.properties.SecurityConfigProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 安全组件Properties
 *
 * @author benym
 * @date 2023/9/4 13:56
 */
@ConfigurationProperties(SecurityProperties.PREFIX)
public class SecurityProperties implements SecurityConfigProvider {

    /**
     * 前缀
     */
    public static final String PREFIX = "rpamis.security";

    /**
     * 支持的加密算法：sm4
     */
    @NestedConfigurationProperty
    private Algorithm algorithm;

    /**
     * 是否启用脱敏切面
     */
    private Boolean desensitizationEnable = Boolean.TRUE;

    /**
     * 是否启用安全组件
     */
    private Boolean enable;

    /**
     * 忽略解密失败，如果解密失败则返回原值
     */
    private Boolean ignoreDecryptFailed = Boolean.TRUE;

    /**
     * 自定义切点
     */
    private String customPointcut = "";

    @Override
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getDesensitizationEnable() {
        return desensitizationEnable;
    }

    public void setDesensitizationEnable(Boolean desensitizationEnable) {
        this.desensitizationEnable = desensitizationEnable;
    }

    @Override
    public Boolean getIgnoreDecryptFailed() {
        return ignoreDecryptFailed;
    }

    public void setIgnoreDecryptFailed(Boolean ignoreDecryptFailed) {
        this.ignoreDecryptFailed = ignoreDecryptFailed;
    }

    public String getCustomPointcut() {
        return customPointcut;
    }

    public void setCustomPointcut(String customPointcut) {
        this.customPointcut = customPointcut;
    }
}
