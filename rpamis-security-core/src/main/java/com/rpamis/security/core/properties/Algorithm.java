package com.rpamis.security.core.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.StringUtils;

import javax.validation.Valid;

/**
 * 加解密算法实体
 *
 * @author benym
 * @date 2026/1/8 16:32
 */
public class Algorithm {

    /**
     * 全局加解密算法
     */
    public String active;

    /**
     * SM4
     */
    @Valid
    @NestedConfigurationProperty
    public Sm4Config sm4;

    public String getActive() {
        return active;
    }

    public DefaultPrefix getEnumActive() {
        try {
            if (!StringUtils.hasText(active)) {
                return null;
            }
            return DefaultPrefix.valueOf(active.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DefaultPrefix.SM4;
        }
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Sm4Config getSm4() {
        return sm4;
    }

    public void setSm4(Sm4Config sm4) {
        this.sm4 = sm4;
    }
}
