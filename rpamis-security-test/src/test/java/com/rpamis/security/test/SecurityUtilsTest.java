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

import com.rpamis.security.core.algorithm.SecurityAlgorithm;
import com.rpamis.security.core.properties.Algorithm;
import com.rpamis.security.core.properties.DefaultPrefix;
import com.rpamis.security.core.properties.SecurityConfigProvider;
import com.rpamis.security.core.properties.Sm4Config;
import com.rpamis.security.core.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

/**
 * SecurityUtils 测试类
 *
 * @author benym
 * @since 2026/1/28
 */
public class SecurityUtilsTest {

	private SecurityAlgorithm securityAlgorithm;

	private SecurityConfigProvider securityConfigProvider;

	private Algorithm algorithm;

	private Sm4Config sm4Config;

	@BeforeEach
	public void setUp() {
		// 重置静态前缀映射，确保测试之间互不影响
		resetPrefixMaps();

		securityAlgorithm = Mockito.mock(SecurityAlgorithm.class);
		securityConfigProvider = Mockito.mock(SecurityConfigProvider.class);
		algorithm = Mockito.mock(Algorithm.class);
		sm4Config = Mockito.mock(Sm4Config.class);

		when(securityConfigProvider.getAlgorithm()).thenReturn(algorithm);
		when(algorithm.getSm4()).thenReturn(sm4Config);
		when(algorithm.getEnumActive()).thenReturn(DefaultPrefix.SM4);
		when(sm4Config.getPrefix()).thenReturn(DefaultPrefix.SM4.getPrefix());
	}

	/**
	 * 重置静态前缀映射
	 */
	private void resetPrefixMaps() {
		try {
			Field customPrefixMapField = SecurityUtils.class.getDeclaredField("CUSTOM_ALGORITHM_PREFIX_MAP");
			customPrefixMapField.setAccessible(true);
			@SuppressWarnings("unchecked")
			ConcurrentHashMap<String, String> customPrefixMap = (ConcurrentHashMap<String, String>) customPrefixMapField
				.get(null);
			customPrefixMap.clear();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试空值加密 验证对空字符串和null值进行加密时的行为
	 */
	@Test
	@DisplayName("测试空值加密")
	public void testEncryptWithPrefixWithEmptySource() {
		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		assertEquals("", securityUtils.encryptWithPrefix(""));
		assertNull(securityUtils.encryptWithPrefix(null));
	}

	/**
	 * 测试已加密数据加密 验证对已加密数据进行加密时不会重复加密
	 */
	@Test
	@DisplayName("测试已加密数据加密")
	public void testEncryptWithPrefixWithAlreadyEncryptedSource() {
		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		String encryptedValue = DefaultPrefix.SM4.getPrefix() + "encrypted";
		assertEquals(encryptedValue, securityUtils.encryptWithPrefix(encryptedValue));
	}

	/**
	 * 测试有效数据加密
	 * 验证对有效数据进行加密时能够正确添加前缀
	 */
	@Test
	@DisplayName("测试有效数据加密")
    public void testEncryptWithPrefixWithValidSource() {
        // 重置 mock 状态，确保不受其他测试影响
        when(sm4Config.getPrefix()).thenReturn(DefaultPrefix.SM4.getPrefix());

        String source = "test";
        String encrypted = "encrypted";
        when(securityAlgorithm.encrypt(source)).thenReturn(encrypted);

        SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
        String result = securityUtils.encryptWithPrefix(source);

        assertEquals(DefaultPrefix.SM4.getPrefix() + encrypted, result);
        Mockito.verify(securityAlgorithm, Mockito.times(1)).encrypt(source);
    }

	/**
	 * 测试空值解密 验证对空字符串和null值进行解密时的行为
	 */
	@Test
	@DisplayName("测试空值解密")
	public void testDecryptWithPrefixWithEmptySource() {
		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		assertEquals("", securityUtils.decryptWithPrefix(""));
		assertNull(securityUtils.decryptWithPrefix(null));
	}

	/**
	 * 测试已加密数据解密 验证对已加密数据进行解密时能够正确移除前缀
	 */
	@Test
	@DisplayName("测试已加密数据解密")
	public void testDecryptWithPrefixWithEncryptedSource() {
		String encrypted = "encrypted";
		String decrypted = "decrypted";
		when(securityAlgorithm.decrypt(encrypted)).thenReturn(decrypted);

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		String result = securityUtils.decryptWithPrefix(DefaultPrefix.SM4.getPrefix() + encrypted);

		assertEquals(decrypted, result);
		Mockito.verify(securityAlgorithm, Mockito.times(1)).decrypt(encrypted);
	}

	/**
	 * 测试未加密数据解密 验证对未加密数据进行解密时的行为
	 */
	@Test
	@DisplayName("测试未加密数据解密")
	public void testDecryptWithPrefixWithNonEncryptedSource() {
		String source = "test";
		String decrypted = "decrypted";
		when(securityAlgorithm.decrypt(source)).thenReturn(decrypted);

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		String result = securityUtils.decryptWithPrefix(source);

		assertEquals(decrypted, result);
		Mockito.verify(securityAlgorithm, Mockito.times(1)).decrypt(source);
	}

	/**
	 * 测试空值加密状态检查 验证对空字符串和null值进行加密状态检查时的行为
	 */
	@Test
	@DisplayName("测试空值加密状态检查")
	public void testCheckHasBeenEncryptedWithEmptySource() {
		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		assertEquals(false, securityUtils.checkHasBeenEncrypted(""));
		assertEquals(false, securityUtils.checkHasBeenEncrypted(null));
	}

	/**
	 * 测试默认前缀加密状态检查 验证对带有默认前缀的数据进行加密状态检查时的行为
	 */
	@Test
	@DisplayName("测试默认前缀加密状态检查")
	public void testCheckHasBeenEncryptedWithDefaultPrefix() {
		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		String encryptedValue = DefaultPrefix.SM4.getPrefix() + "encrypted";
		assertEquals(true, securityUtils.checkHasBeenEncrypted(encryptedValue));
	}

	/**
	 * 测试未加密值加密状态检查 验证对未加密值进行加密状态检查时的行为
	 */
	@Test
	@DisplayName("测试未加密值加密状态检查")
	public void testCheckHasBeenEncryptedWithNonEncryptedValue() {
		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		assertEquals(false, securityUtils.checkHasBeenEncrypted("non-encrypted"));
	}

	/**
	 * 测试自定义前缀加密状态检查 验证对带有自定义前缀的数据进行加密状态检查时的行为
	 */
	@Test
	@DisplayName("测试自定义前缀加密状态检查")
	public void testCheckHasBeenEncryptedWithCustomPrefix() {
		// 创建新的 mock 对象，确保不受其他测试影响
		SecurityAlgorithm customSecurityAlgorithm = Mockito.mock(SecurityAlgorithm.class);
		SecurityConfigProvider customSecurityConfigProvider = Mockito.mock(SecurityConfigProvider.class);
		Algorithm customAlgorithm = Mockito.mock(Algorithm.class);
		Sm4Config customSm4Config = Mockito.mock(Sm4Config.class);

		when(customSecurityConfigProvider.getAlgorithm()).thenReturn(customAlgorithm);
		when(customAlgorithm.getSm4()).thenReturn(customSm4Config);
		when(customAlgorithm.getEnumActive()).thenReturn(DefaultPrefix.SM4);

		// 设置自定义前缀
		String customPrefix = "CUSTOM_";
		when(customSm4Config.getPrefix()).thenReturn(customPrefix);

		SecurityUtils securityUtils = new SecurityUtils(customSecurityAlgorithm, customSecurityConfigProvider);
		String encryptedValue = customPrefix + "encrypted";
		assertEquals(true, securityUtils.checkHasBeenEncrypted(encryptedValue));
	}

	/**
	 * 测试自定义前缀初始化 验证初始化时会正确添加自定义前缀到CUSTOM_ALGORITHM_PREFIX_MAP
	 */
	@Test
	@DisplayName("测试自定义前缀初始化")
	public void testInitPropertyPrefixWithCustomPrefix() {
		// 创建新的 mock 对象，确保不受其他测试影响
		SecurityAlgorithm customSecurityAlgorithm = Mockito.mock(SecurityAlgorithm.class);
		SecurityConfigProvider customSecurityConfigProvider = Mockito.mock(SecurityConfigProvider.class);
		Algorithm customAlgorithm = Mockito.mock(Algorithm.class);
		Sm4Config customSm4Config = Mockito.mock(Sm4Config.class);

		when(customSecurityConfigProvider.getAlgorithm()).thenReturn(customAlgorithm);
		when(customAlgorithm.getSm4()).thenReturn(customSm4Config);
		when(customAlgorithm.getEnumActive()).thenReturn(DefaultPrefix.SM4);

		// 设置自定义前缀
		String customPrefix = "CUSTOM_";
		when(customSm4Config.getPrefix()).thenReturn(customPrefix);

		// 验证初始化时会添加自定义前缀到 CUSTOM_ALGORITHM_PREFIX_MAP
		SecurityUtils securityUtils = new SecurityUtils(customSecurityAlgorithm, customSecurityConfigProvider);
		// 通过测试其他方法间接验证自定义前缀是否被正确初始化
		String encrypted = "encrypted";
		when(customSecurityAlgorithm.encrypt("test")).thenReturn(encrypted);

		String result = securityUtils.encryptWithPrefix("test");
		assertEquals(customPrefix + encrypted, result);
	}

	/**
	 * 测试非SM4算法前缀添加 验证对非SM4算法进行加密时不会添加前缀
	 */
	@Test
	@DisplayName("测试非SM4算法前缀添加")
	public void testAddAlgorithmPrefixWithNonSm4Algorithm() {
		// 创建新的 mock 对象，确保不受其他测试影响
		SecurityAlgorithm customSecurityAlgorithm = Mockito.mock(SecurityAlgorithm.class);
		SecurityConfigProvider customSecurityConfigProvider = Mockito.mock(SecurityConfigProvider.class);
		Algorithm customAlgorithm = Mockito.mock(Algorithm.class);
		Sm4Config customSm4Config = Mockito.mock(Sm4Config.class);

		when(customSecurityConfigProvider.getAlgorithm()).thenReturn(customAlgorithm);
		when(customAlgorithm.getSm4()).thenReturn(customSm4Config);
		when(customAlgorithm.getEnumActive()).thenReturn(null); // 设置为非 SM4 算法
		when(customSm4Config.getPrefix()).thenReturn(DefaultPrefix.SM4.getPrefix());

		SecurityUtils securityUtils = new SecurityUtils(customSecurityAlgorithm, customSecurityConfigProvider);
		String encrypted = "encrypted";
		// 由于我们无法直接调用私有方法 addAlgorithmPrefix，我们通过调用 encryptWithPrefix 来间接测试
		when(customSecurityAlgorithm.encrypt("test")).thenReturn(encrypted);

		String result = securityUtils.encryptWithPrefix("test");
		assertEquals(encrypted, result);
	}

	/**
	 * 测试非SM4算法前缀移除 验证对非SM4算法进行解密时不会移除前缀
	 */
	@Test
	@DisplayName("测试非SM4算法前缀移除")
	public void testRemoveAlgorithmPrefixWithNonSm4Algorithm() {
		// 创建新的 mock 对象，确保不受其他测试影响
		SecurityAlgorithm customSecurityAlgorithm = Mockito.mock(SecurityAlgorithm.class);
		SecurityConfigProvider customSecurityConfigProvider = Mockito.mock(SecurityConfigProvider.class);
		Algorithm customAlgorithm = Mockito.mock(Algorithm.class);
		Sm4Config customSm4Config = Mockito.mock(Sm4Config.class);

		when(customSecurityConfigProvider.getAlgorithm()).thenReturn(customAlgorithm);
		when(customAlgorithm.getSm4()).thenReturn(customSm4Config);
		when(customAlgorithm.getEnumActive()).thenReturn(null); // 设置为非 SM4 算法
		when(customSm4Config.getPrefix()).thenReturn(DefaultPrefix.SM4.getPrefix());

		SecurityUtils securityUtils = new SecurityUtils(customSecurityAlgorithm, customSecurityConfigProvider);
		String encrypted = "encrypted";
		String decrypted = "decrypted";
		// 由于我们无法直接调用私有方法 removeAlgorithmPrefix，我们通过调用 decryptWithPrefix 来间接测试
		when(customSecurityAlgorithm.decrypt(encrypted)).thenReturn(decrypted);

		String result = securityUtils.decryptWithPrefix(encrypted);
		assertEquals(decrypted, result);
	}

}
