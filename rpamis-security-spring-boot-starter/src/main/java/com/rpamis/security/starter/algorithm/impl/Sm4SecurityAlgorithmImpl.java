package com.rpamis.security.starter.algorithm.impl;

import com.rpamis.security.starter.algorithm.SecurityAlgorithm;
import com.rpamis.security.starter.autoconfigure.SecurityProperties;
import com.rpamis.security.starter.utils.Sm4SecurityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * Sm4国密对称加密算法实现
 *
 * @author benym
 * @date 2023/9/4 14:29
 */
public class Sm4SecurityAlgorithmImpl implements SecurityAlgorithm, InitializingBean {

    private final SecurityProperties securityProperties;

    private final boolean ignoreDecryptFailed;

    public Sm4SecurityAlgorithmImpl(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        this.ignoreDecryptFailed = securityProperties.getIgnoreDecryptFailed();
    }

    @Override
    public String encrypt(String sourceValue) {
        if (!StringUtils.hasLength(sourceValue)) {
            return sourceValue;
        }
        return Sm4SecurityUtils.encrypted(sourceValue, securityProperties.getSm4key());
    }

    @Override
    public String decrypt(String sourceValue) {
        if (!StringUtils.hasLength(sourceValue)) {
            return sourceValue;
        }
        try {
            return Sm4SecurityUtils.decrypted(sourceValue, securityProperties.getSm4key());
        } catch (SecurityException e) {
            if (ignoreDecryptFailed) {
                return sourceValue;
            } else {
                throw e;
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasLength(securityProperties.getSm4key())) {
            throw new IllegalArgumentException("sm4 encryption key is null, please set");
        }
    }
}
