package com.rpamis.security.core.properties;

/**
 * 加解密唯一识别前缀默认值
 *
 * @author benym
 * @since 2026/1/9 16:57
 */
public enum DefaultPrefix {

	/**
	 * SM4默认前缀
	 */
	SM4("ENC_SM4_");

	private final String prefix;

	DefaultPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

}
