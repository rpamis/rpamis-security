package com.rpamis.security.core.properties;

/**
 * 安全配置提供者
 *
 * @author benym
 * @since 2025/9/11 18:18
 */
public interface SecurityConfigProvider {

	/**
	 * 获取加解密算法实体
	 * @return String
	 */
	Algorithm getAlgorithm();

	/**
	 * 获取是否忽略解密失败
	 * @return Boolean
	 */
	Boolean getIgnoreDecryptFailed();

}
