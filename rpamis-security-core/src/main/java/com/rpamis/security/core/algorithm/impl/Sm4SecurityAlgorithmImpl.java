package com.rpamis.security.core.algorithm.impl;

import com.rpamis.security.core.algorithm.SecurityAlgorithm;
import com.rpamis.security.core.algorithm.SecurityConfigProvider;
import com.rpamis.security.core.utils.Sm4SecurityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * Sm4国密对称加密算法实现
 *
 * @author benym
 * @date 2023/9/4 14:29
 */
public class Sm4SecurityAlgorithmImpl implements SecurityAlgorithm, InitializingBean {

	private final SecurityConfigProvider securityConfigProvider;

	private final boolean ignoreDecryptFailed;

	public Sm4SecurityAlgorithmImpl(SecurityConfigProvider securityConfigProvider) {
		this.securityConfigProvider = securityConfigProvider;
		this.ignoreDecryptFailed = securityConfigProvider.getIgnoreDecryptFailed();
	}

	@Override
	public String encrypt(String sourceValue) {
		if (!StringUtils.hasLength(sourceValue)) {
			return sourceValue;
		}
		return Sm4SecurityUtils.encrypted(sourceValue, securityConfigProvider.getSm4key());
	}

	@Override
	public String decrypt(String sourceValue) {
		if (!StringUtils.hasLength(sourceValue)) {
			return sourceValue;
		}
		try {
			return Sm4SecurityUtils.decrypted(sourceValue, securityConfigProvider.getSm4key());
		}
		catch (SecurityException e) {
			if (ignoreDecryptFailed) {
				return sourceValue;
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!StringUtils.hasLength(securityConfigProvider.getSm4key())) {
			throw new IllegalArgumentException("sm4 encryption key is null, please set");
		}
	}

}
