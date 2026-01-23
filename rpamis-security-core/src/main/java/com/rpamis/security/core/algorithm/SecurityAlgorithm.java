package com.rpamis.security.core.algorithm;

/**
 * 加解密算法接口
 *
 * @author benym
 * @since 2023/8/31 17:14
 */
public interface SecurityAlgorithm {

    /**
     * 加密原始字段
     *
     * @param sourceValue 原始加密字段
     * @return String
     */
    String encrypt(String sourceValue);

    /**
     * 解密已加密字段
     *
     * @param sourceValue 原始已加密字段
     * @return String
     */
    String decrypt(String sourceValue);
}
