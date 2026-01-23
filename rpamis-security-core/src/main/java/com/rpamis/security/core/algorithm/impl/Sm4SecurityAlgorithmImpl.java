/*
 * Copyright 2023-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rpamis.security.core.algorithm.impl;

import com.rpamis.security.core.algorithm.SecurityAlgorithm;
import com.rpamis.security.core.properties.SecurityConfigProvider;
import com.rpamis.security.core.utils.Sm4SecurityUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * Sm4国密对称加密算法实现
 *
 * @author benym
 * @since 2023/9/4 14:29
 */
public class Sm4SecurityAlgorithmImpl implements SecurityAlgorithm, InitializingBean {

	private final boolean ignoreDecryptFailed;

	private final String key;

	public Sm4SecurityAlgorithmImpl(SecurityConfigProvider securityConfigProvider) {
		this.ignoreDecryptFailed = securityConfigProvider.getIgnoreDecryptFailed();
		this.key = securityConfigProvider.getAlgorithm().getSm4().getKey();
	}

	@Override
	public String encrypt(String sourceValue) {
		if (!StringUtils.hasLength(sourceValue)) {
			return sourceValue;
		}
		return Sm4SecurityUtils.encrypted(sourceValue, key);
	}

	@Override
	public String decrypt(String sourceValue) {
		if (!StringUtils.hasLength(sourceValue)) {
			return sourceValue;
		}
		try {
			return Sm4SecurityUtils.decrypted(sourceValue, key);
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
		if (!StringUtils.hasLength(key)) {
			throw new IllegalArgumentException("sm4 encryption key is null, please set");
		}
	}

}
