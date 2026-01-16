package com.rpamis.security.core.properties;

/**
 * SM4配置类
 *
 * @author benym
 * @date 2026/1/7 14:06
 */
public class Sm4Config {

    /**
     * SM4密钥，必须为16位
     */
    public String key;

    /**
     * 加解密唯一识别前缀
     */
    public String prefix = DefaultPrefix.SM4.getPrefix();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
