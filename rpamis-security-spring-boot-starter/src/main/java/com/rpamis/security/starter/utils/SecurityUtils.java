package com.rpamis.security.starter.utils;

import com.rpamis.security.starter.algorithm.SecurityAlgorithm;
import com.rpamis.security.starter.autoconfigure.SecurityProperties;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 安全组件工具类
 *
 * @author benym
 * @date 2026/1/7 18:07
 */
public class SecurityUtils {

    /**
     * 加密算法类型->加密算法前缀占位符 Map
     */
    private final ConcurrentHashMap<SecurityProperties.Algorithm, String> algorithmPrefixMap = new ConcurrentHashMap<>() {{
        for (SecurityProperties.Algorithm algorithm : SecurityProperties.Algorithm.values()) {
            put(algorithm, "ENC_" + algorithm.name() + "_");
        }
    }};

    /**
     * 加密算法
     */
    private final SecurityAlgorithm securityAlgorithm;

    /**
     * 安全组件配置
     */
    private final SecurityProperties securityProperties;

    public SecurityUtils(SecurityAlgorithm securityAlgorithm, SecurityProperties securityProperties) {
        this.securityAlgorithm = securityAlgorithm;
        this.securityProperties = securityProperties;
    }

    /**
     * 处理加密-带前缀
     *
     * @param source 原待加密值
     * @return 加密后带前缀的值
     */
    public String encryptWithPrefix(String source) {
        if (!StringUtils.hasText(source)) {
            return source;
        }
        if (checkHasBeenEncrypted(source)) {
            return source;
        }
        String encryptedString = securityAlgorithm.encrypt(source);
        return addAlgorithmPrefix(encryptedString);
    }

    /**
     * 处理解密-移除前缀
     *
     * @param source 原待解密值
     * @return 移除前缀解密后值
     */
    public String decryptWithPrefix(String source) {
        if (!StringUtils.hasText(source)) {
            return source;
        }
        String stringObject = source;
        if (checkHasBeenEncrypted(source)) {
            stringObject = removeAlgorithmPrefix(source);
        }
        return securityAlgorithm.decrypt(stringObject);
    }

    /**
     * 校验是否已经加密
     *
     * @param source source
     * @return Boolean
     */
    public Boolean checkHasBeenEncrypted(String source) {
        if (!StringUtils.hasText(source)) {
            return false;
        }
        Collection<String> algorithmsPlaceholder = algorithmPrefixMap.values();
        for (String algorithmPlaceholder : algorithmsPlaceholder) {
            if (source.startsWith(algorithmPlaceholder)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接算法前缀
     *
     * @param encryptedString 加密后的字符串
     * @return 拼接算法前缀后的加密字符串
     */
    private String addAlgorithmPrefix(String encryptedString) {
        if (!StringUtils.hasText(encryptedString)) {
            return encryptedString;
        }
        SecurityProperties.Algorithm algorithm = securityProperties.getAlgorithm();
        if (algorithm == null) {
            return encryptedString;
        }
        String algorithmPlaceholder = algorithmPrefixMap.get(algorithm);
        if (!StringUtils.hasText(algorithmPlaceholder)) {
            return encryptedString;
        } else {
            return algorithmPlaceholder + encryptedString;
        }
    }

    /**
     * 移除算法前缀
     *
     * @param decryptedString 解密后的字符串
     * @return 移除算法前缀后的解密字符串
     */
    private String removeAlgorithmPrefix(String decryptedString) {
        if (!StringUtils.hasText(decryptedString)) {
            return decryptedString;
        }
        SecurityProperties.Algorithm algorithm = securityProperties.getAlgorithm();
        if (algorithm == null) {
            return decryptedString;
        }
        String algorithmPlaceholder = algorithmPrefixMap.get(algorithm);
        if (!StringUtils.hasText(algorithmPlaceholder)) {
            return decryptedString;
        } else {
            if (decryptedString.startsWith(algorithmPlaceholder)) {
                return decryptedString.substring(algorithmPlaceholder.length());
            } else {
                return decryptedString;
            }
        }
    }
}
