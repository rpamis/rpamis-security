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
import com.rpamis.security.core.properties.SecurityConfigProvider;
import com.rpamis.security.core.utils.FieldUtils;
import com.rpamis.security.core.utils.MaskAnnotationResolver;
import com.rpamis.security.core.utils.SecurityResolver;
import com.rpamis.security.core.utils.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 工具类测试
 *
 * @author benym
 * @since 2026/1/28 9:40
 */
public class UtilsTest {

	/**
	 * 测试FieldUtils.getAllFields方法 验证能够正确获取对象的所有字段
	 */
	@Test
	@DisplayName("FieldUtils getAllFields 测试")
	public void testFieldUtilsGetAllFields() {
		// 测试普通对象
		TestVO testVO = new TestVO();
		testVO.setName("test");
		testVO.setIdCard("123456789012345678");
		testVO.setPhone("13800138000");

		// 测试获取所有字段
		java.lang.reflect.Field[] fields = FieldUtils.getAllFields(testVO);
		assertThat(fields).isNotNull();
		assertThat(fields.length).isGreaterThan(0);
	}

	/**
	 * 测试MaskAnnotationResolver.isNotBaseType方法 验证能够正确判断类型是否为非基础类型
	 */
	@Test
	@DisplayName("MaskAnnotationResolver isNotBaseType 测试")
	public void testMaskAnnotationResolverIsNotBaseType() {
		Set<Object> referenceCounter = Collections.newSetFromMap(new IdentityHashMap<>());

		// 测试基础类型
		boolean result1 = MaskAnnotationResolver.isNotBaseType(int.class, 1, referenceCounter);
		assertThat(result1).isFalse();

		// 测试字符串类型
		boolean result2 = MaskAnnotationResolver.isNotBaseType(String.class, "test", referenceCounter);
		assertThat(result2).isFalse();

		// 测试自定义类型
		TestVO testVO = new TestVO();
		boolean result3 = MaskAnnotationResolver.isNotBaseType(TestVO.class, testVO, referenceCounter);
		assertThat(result3).isTrue();

		// 测试枚举类型
		boolean result4 = MaskAnnotationResolver.isNotBaseType(Enum.class, Enum.class, referenceCounter);
		assertThat(result4).isFalse();
	}

	/**
	 * 测试SecurityResolver处理空值 验证能够正确处理空值的加密和解密
	 */
	@Test
	@DisplayName("SecurityResolver 空值测试")
	public void testSecurityResolverNullValues() {
		// 创建模拟的 SecurityConfigProvider
		SecurityConfigProvider securityConfigProvider = new SecurityConfigProvider() {
			@Override
			public Algorithm getAlgorithm() {
				return new Algorithm();
			}

			@Override
			public Boolean getIgnoreDecryptFailed() {
				return true;
			}
		};

		// 创建模拟的 SecurityAlgorithm
		SecurityAlgorithm securityAlgorithm = new SecurityAlgorithm() {
			@Override
			public String encrypt(String source) {
				return source;
			}

			@Override
			public String decrypt(String source) {
				return source;
			}
		};

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		SecurityResolver securityResolver = new SecurityResolver(securityUtils);

		// 测试加密空值
		Object encryptResult = securityResolver.encryptFiled(null);
		assertThat(encryptResult).isNull();

		// 测试解密空值
		Object decryptResult = securityResolver.decryptFiled(null);
		assertThat(decryptResult).isNull();
	}

	/**
	 * 测试SecurityResolver处理空列表 验证能够正确处理空列表的加密
	 */
	@Test
	@DisplayName("SecurityResolver 空列表测试")
	public void testSecurityResolverEmptyList() {
		// 创建模拟的 SecurityConfigProvider
		SecurityConfigProvider securityConfigProvider = new SecurityConfigProvider() {
			@Override
			public Algorithm getAlgorithm() {
				return new Algorithm();
			}

			@Override
			public Boolean getIgnoreDecryptFailed() {
				return true;
			}
		};

		// 创建模拟的 SecurityAlgorithm
		SecurityAlgorithm securityAlgorithm = new SecurityAlgorithm() {
			@Override
			public String encrypt(String source) {
				return source;
			}

			@Override
			public String decrypt(String source) {
				return source;
			}
		};

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		SecurityResolver securityResolver = new SecurityResolver(securityUtils);

		// 测试加密空列表
		List<Object> emptyList = new ArrayList<>();
		Object encryptResult = securityResolver.encryptFiled(emptyList);
		assertThat(encryptResult).isNotNull();
	}

	/**
	 * 测试SecurityResolver处理非字符串类型 验证能够正确处理包含非字符串类型的对象的加密
	 */
	@Test
	@DisplayName("SecurityResolver 非字符串类型测试")
	public void testSecurityResolverNonStringType() {
		// 创建模拟的 SecurityConfigProvider
		SecurityConfigProvider securityConfigProvider = new SecurityConfigProvider() {
			@Override
			public Algorithm getAlgorithm() {
				return new Algorithm();
			}

			@Override
			public Boolean getIgnoreDecryptFailed() {
				return true;
			}
		};

		// 创建模拟的 SecurityAlgorithm
		SecurityAlgorithm securityAlgorithm = new SecurityAlgorithm() {
			@Override
			public String encrypt(String source) {
				return source;
			}

			@Override
			public String decrypt(String source) {
				return source;
			}
		};

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		SecurityResolver securityResolver = new SecurityResolver(securityUtils);

		// 测试包含非字符串类型的对象
		TestVO testVO = new TestVO();
		testVO.setName("test");
		testVO.setIdCard("123456789012345678");
		testVO.setPhone("13800138000");

		// 测试加密
		Object encryptResult = securityResolver.encryptFiled(testVO);
		assertThat(encryptResult).isNotNull();
	}

	/**
	 * 测试SecurityResolver处理非列表类型解密 验证能够正确处理非列表类型对象的解密
	 */
	@Test
	@DisplayName("SecurityResolver 非列表类型解密测试")
	public void testSecurityResolverNonListTypeDecrypt() {
		// 创建模拟的 SecurityConfigProvider
		SecurityConfigProvider securityConfigProvider = new SecurityConfigProvider() {
			@Override
			public Algorithm getAlgorithm() {
				return new Algorithm();
			}

			@Override
			public Boolean getIgnoreDecryptFailed() {
				return true;
			}
		};

		// 创建模拟的 SecurityAlgorithm
		SecurityAlgorithm securityAlgorithm = new SecurityAlgorithm() {
			@Override
			public String encrypt(String source) {
				return source;
			}

			@Override
			public String decrypt(String source) {
				return source;
			}
		};

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		SecurityResolver securityResolver = new SecurityResolver(securityUtils);

		// 测试非列表类型的解密
		TestVO testVO = new TestVO();
		testVO.setName("test");
		testVO.setIdCard("123456789012345678");
		testVO.setPhone("13800138000");

		// 测试解密
		Object decryptResult = securityResolver.decryptFiled(testVO);
		assertThat(decryptResult).isNotNull();
	}

	/**
	 * 测试SecurityResolver处理加密异常 验证当加密过程中发生异常时，系统能够正确处理
	 */
	@Test
	@DisplayName("SecurityResolver 加密异常测试")
	public void testSecurityResolverEncryptionException() {
		// 创建模拟的 SecurityConfigProvider
		SecurityConfigProvider securityConfigProvider = new SecurityConfigProvider() {
			@Override
			public Algorithm getAlgorithm() {
				return new Algorithm();
			}

			@Override
			public Boolean getIgnoreDecryptFailed() {
				return true;
			}
		};

		// 创建模拟的 SecurityAlgorithm，故意抛出异常
		SecurityAlgorithm securityAlgorithm = new SecurityAlgorithm() {
			@Override
			public String encrypt(String source) {
				throw new RuntimeException("Encryption error");
			}

			@Override
			public String decrypt(String source) {
				return source;
			}
		};

		SecurityUtils securityUtils = new SecurityUtils(securityAlgorithm, securityConfigProvider);
		SecurityResolver securityResolver = new SecurityResolver(securityUtils);

		// 测试加密异常
		TestVO testVO = new TestVO();
		testVO.setName("test");
		testVO.setIdCard("123456789012345678");
		testVO.setPhone("13800138000");

		// 测试加密
		Object encryptResult = securityResolver.encryptFiled(testVO);
		assertThat(encryptResult).isNotNull();
	}

	// 测试用的VO类
	static class TestVO {

		private String name;

		private String idCard;

		private String phone;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIdCard() {
			return idCard;
		}

		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

	}

}
