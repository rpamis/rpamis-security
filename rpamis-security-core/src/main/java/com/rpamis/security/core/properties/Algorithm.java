package com.rpamis.security.core.properties;

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
    public DefaultPrefix active = DefaultPrefix.SM4;

    /**
     * SM4
     */
    @Valid
    public Sm4Config sm4;

    public DefaultPrefix getActive() {
        return active;
    }

    public void setActive(DefaultPrefix active) {
        this.active = active;
    }

    public Sm4Config getSm4() {
        return sm4;
    }

    public void setSm4(Sm4Config sm4) {
        this.sm4 = sm4;
    }
}
