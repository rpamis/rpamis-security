package com.rpamis.security.core.algorithm;

/**
 * 安全配置提供者
 *
 * @author benym
 * @date 2025/9/11 18:18
 */
public interface SecurityConfigProvider {

	/**
	 * 获取sm4密钥
	 * @return String
	 */
	String getSm4key();

	/**
	 * 获取是否忽略解密失败
	 * @return Boolean
	 */
	Boolean getIgnoreDecryptFailed();

}
