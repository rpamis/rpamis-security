package com.rpamis.security.starter.properties;

import javax.validation.constraints.Size;

/**
 * SM4配置类
 *
 * @author benym
 * @date 2026/1/7 14:06
 */
public class Sm4Config {

    @Size(min = 16, max = 16, message = "SM4密钥长度必须为16位")
    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
