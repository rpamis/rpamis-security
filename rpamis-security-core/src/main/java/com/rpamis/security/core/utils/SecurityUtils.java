package com.rpamis.security.core.utils;

import com.rpamis.security.core.algorithm.SecurityAlgorithm;
import com.rpamis.security.core.properties.Algorithm;
import com.rpamis.security.core.properties.DefaultPrefix;
import com.rpamis.security.core.properties.SecurityConfigProvider;
import com.rpamis.security.core.properties.Sm4Config;
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
     * 加密算法类型->默认加密算法前缀占位符 Map
     */
    private static final ConcurrentHashMap<String, String> DEFAULT_ALGORITHM_PREFIX_MAP = new ConcurrentHashMap<>();

    /**
     * 加密算法类型->自定义加密算法前缀占位符 Map
     */
    private static final ConcurrentHashMap<String, String> CUSTOM_ALGORITHM_PREFIX_MAP = new ConcurrentHashMap<>();

    static {
        for (DefaultPrefix defaultPrefix : DefaultPrefix.values()) {
            DEFAULT_ALGORITHM_PREFIX_MAP.put(defaultPrefix.name(), defaultPrefix.getPrefix());
        }
    }

    /**
     * 加密算法
     */
    private final SecurityAlgorithm securityAlgorithm;

    /**
     * 安全组件配置
     */
    private final SecurityConfigProvider securityConfigProvider;

    public SecurityUtils(SecurityAlgorithm securityAlgorithm, SecurityConfigProvider securityConfigProvider) {
        this.securityAlgorithm = securityAlgorithm;
        this.securityConfigProvider = securityConfigProvider;
        initPropertyPrefix();
    }

    /**
     * 初始化配置的前缀
     */
    private void initPropertyPrefix() {
        Algorithm algorithm = securityConfigProvider.getAlgorithm();
        Sm4Config sm4 = algorithm.getSm4();
        if (sm4 != null && StringUtils.hasText(sm4.getPrefix()) && !sm4.getPrefix().equals(DefaultPrefix.SM4.getPrefix())) {
            CUSTOM_ALGORITHM_PREFIX_MAP.put(DefaultPrefix.SM4.name(), sm4.getPrefix());
        }
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
        Collection<String> defaultPrefixList = DEFAULT_ALGORITHM_PREFIX_MAP.values();
        for (String defaultPrefix : defaultPrefixList) {
            if (source.startsWith(defaultPrefix)) {
                return true;
            }
        }
        Collection<String> customPrefixList = CUSTOM_ALGORITHM_PREFIX_MAP.values();
        for (String customPrefix : customPrefixList) {
            if (StringUtils.hasText(customPrefix) && source.startsWith(customPrefix)) {
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
        Algorithm algorithm = securityConfigProvider.getAlgorithm();
        if (algorithm == null) {
            return encryptedString;
        }
        DefaultPrefix active = algorithm.getEnumActive();
        if (active == DefaultPrefix.SM4) {
            String defaultSm4Prefix = DEFAULT_ALGORITHM_PREFIX_MAP.get(active.name());
            String customSm4Prefix = CUSTOM_ALGORITHM_PREFIX_MAP.get(active.name());
            if (StringUtils.hasText(customSm4Prefix)) {
                return customSm4Prefix + encryptedString;
            } else if (StringUtils.hasText(defaultSm4Prefix)) {
                return defaultSm4Prefix + encryptedString;
            } else {
                return encryptedString;
            }
        } else {
            return encryptedString;
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
        Algorithm algorithm = securityConfigProvider.getAlgorithm();
        if (algorithm == null) {
            return decryptedString;
        }
        DefaultPrefix active = algorithm.getEnumActive();
        if (active == DefaultPrefix.SM4) {
            String defaultSm4Prefix = DEFAULT_ALGORITHM_PREFIX_MAP.get(active.name());
            String customSm4Prefix = CUSTOM_ALGORITHM_PREFIX_MAP.get(active.name());
            if (StringUtils.hasText(customSm4Prefix) && decryptedString.startsWith(customSm4Prefix)) {
                return decryptedString.substring(customSm4Prefix.length());
            } else if (StringUtils.hasText(defaultSm4Prefix)) {
                return decryptedString.substring(defaultSm4Prefix.length());
            } else {
                return decryptedString;
            }
        } else {
            return decryptedString;
        }
    }
}
