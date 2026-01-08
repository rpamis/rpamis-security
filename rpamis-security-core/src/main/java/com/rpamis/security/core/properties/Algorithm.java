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
     * SM4
     */
    @Valid
    public Sm4Config sm4 = new Sm4Config();

    public Sm4Config getSm4() {
        return sm4;
    }

    public void setSm4(Sm4Config sm4) {
        this.sm4 = sm4;
    }
}
