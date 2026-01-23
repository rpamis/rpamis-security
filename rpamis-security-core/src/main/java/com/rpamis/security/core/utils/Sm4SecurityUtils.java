package com.rpamis.security.core.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;

/**
 * Sm4国密算法加解密工具类
 *
 * @author benym
 * @since 2023/9/4 15:10
 */
public class Sm4SecurityUtils {

    private Sm4SecurityUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static volatile SM4 sm4Instance;

    /**
     * 获取单例的SM4实例
     *
     * @param key 密钥
     * @return SM4实例
     */
    private static SM4 getSm4Instance(String key) {
        if (sm4Instance == null) {
            synchronized (Sm4SecurityUtils.class) {
                if (sm4Instance == null) {
                    sm4Instance = SmUtil.sm4(key.getBytes());
                }
            }
        }
        return sm4Instance;
    }

    /**
     * 加密
     *
     * @param source 源值
     * @param key    密钥
     * @return 加密后的值
     */
    public static String encrypted(String source, String key) {
        try {
            SM4 sm4 = getSm4Instance(key);
            return sm4.encryptHex(source);
        } catch (Exception e) {
            throw new SecurityException("encrypt failed", e);
        }
    }

    /**
     * 解密
     *
     * @param source 源值
     * @param key    密钥
     * @return 解密后的值
     */
    public static String decrypted(String source, String key) {
        try {
            SM4 sm4 = getSm4Instance(key);
            return sm4.decryptStr(source);
        } catch (Exception e) {
            throw new SecurityException("decrypt failed", e);
        }
    }
}
