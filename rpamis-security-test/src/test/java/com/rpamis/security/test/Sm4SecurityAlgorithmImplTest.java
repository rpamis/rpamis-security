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
package com.rpamis.security.test;

import com.rpamis.security.core.algorithm.impl.Sm4SecurityAlgorithmImpl;
import com.rpamis.security.core.properties.Algorithm;
import com.rpamis.security.core.properties.SecurityConfigProvider;
import com.rpamis.security.core.properties.Sm4Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Sm4SecurityAlgorithmImpl单元测试
 *
 * @author benym
 * @since 2026/4/6
 */
public class Sm4SecurityAlgorithmImplTest {

	/**
	 * 测试加密空字符串
	 */
	@Test
	@DisplayName("测试加密空字符串")
	public void testEncryptEmptyString() {
		SecurityConfigProvider provider = createSecurityConfigProvider("0123456789abcdef", true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		String result = algorithm.encrypt("");
		assertEquals("", result);
	}

	/**
	 * 测试加密null值
	 */
	@Test
	@DisplayName("测试加密null值")
	public void testEncryptNull() {
		SecurityConfigProvider provider = createSecurityConfigProvider("0123456789abcdef", true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		String result = algorithm.encrypt(null);
		assertNull(result);
	}

	/**
	 * 测试解密空字符串
	 */
	@Test
	@DisplayName("测试解密空字符串")
	public void testDecryptEmptyString() {
		SecurityConfigProvider provider = createSecurityConfigProvider("0123456789abcdef", true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		String result = algorithm.decrypt("");
		assertEquals("", result);
	}

	/**
	 * 测试解密null值
	 */
	@Test
	@DisplayName("测试解密null值")
	public void testDecryptNull() {
		SecurityConfigProvider provider = createSecurityConfigProvider("0123456789abcdef", true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		String result = algorithm.decrypt(null);
		assertNull(result);
	}

	/**
	 * 测试解密失败时忽略异常
	 */
	@Test
	@DisplayName("测试解密失败时忽略异常")
	public void testDecryptFailedWithIgnore() {
		SecurityConfigProvider provider = createSecurityConfigProvider("0123456789abcdef", true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		String invalidEncrypted = "invalid_encrypted_value";
		String result = algorithm.decrypt(invalidEncrypted);
		assertEquals(invalidEncrypted, result);
	}

	/**
	 * 测试解密失败时不忽略异常
	 */
	@Test
	@DisplayName("测试解密失败时不忽略异常")
	public void testDecryptFailedWithoutIgnore() {
		SecurityConfigProvider provider = createSecurityConfigProvider("0123456789abcdef", false);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		String invalidEncrypted = "invalid_encrypted_value";
		assertThrows(SecurityException.class, () -> algorithm.decrypt(invalidEncrypted));
	}

	/**
	 * 测试初始化时key为空
	 */
	@Test
	@DisplayName("测试初始化时key为空")
	public void testAfterPropertiesSetWithNullKey() {
		SecurityConfigProvider provider = createSecurityConfigProvider(null, true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		assertThrows(IllegalArgumentException.class, () -> algorithm.afterPropertiesSet());
	}

	/**
	 * 测试初始化时key为空字符串
	 */
	@Test
	@DisplayName("测试初始化时key为空字符串")
	public void testAfterPropertiesSetWithEmptyKey() {
		SecurityConfigProvider provider = createSecurityConfigProvider("", true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);

		assertThrows(IllegalArgumentException.class, () -> algorithm.afterPropertiesSet());
	}

	/**
	 * 测试正常加密解密流程
	 */
	@Test
	@DisplayName("测试正常加密解密流程")
	public void testNormalEncryptDecrypt() throws Exception {
		String key = "0123456789abcdef";
		SecurityConfigProvider provider = createSecurityConfigProvider(key, true);
		Sm4SecurityAlgorithmImpl algorithm = new Sm4SecurityAlgorithmImpl(provider);
		algorithm.afterPropertiesSet();

		String source = "testValue";
		String encrypted = algorithm.encrypt(source);
		String decrypted = algorithm.decrypt(encrypted);

		assertEquals(source, decrypted);
	}

	/**
	 * 创建模拟的SecurityConfigProvider
	 */
	private SecurityConfigProvider createSecurityConfigProvider(String key, Boolean ignoreDecryptFailed) {
		return new SecurityConfigProvider() {
			@Override
			public Algorithm getAlgorithm() {
				Algorithm algorithm = new Algorithm();
				Sm4Config sm4Config = new Sm4Config();
				sm4Config.setKey(key);
				algorithm.setSm4(sm4Config);
				return algorithm;
			}

			@Override
			public Boolean getIgnoreDecryptFailed() {
				return ignoreDecryptFailed;
			}
		};
	}

}
