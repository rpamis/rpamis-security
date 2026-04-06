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

import com.rpamis.security.annotation.Masked;
import com.rpamis.security.core.factory.MaskFunctionFactory;
import com.rpamis.security.mask.MaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * MaskFunctionFactory单元测试
 *
 * @author benym
 * @since 2026/4/6
 */
public class MaskFunctionFactoryTest {

	private MaskFunctionFactory maskFunctionFactory;

	@BeforeEach
	public void setUp() {
		maskFunctionFactory = new MaskFunctionFactory();
	}

	/**
	 * 测试无脱敏类型
	 */
	@Test
	@DisplayName("测试无脱敏类型 - NO_MASK")
	public void testNoMask() {
		String input = "testValue";
		Masked annotation = createMaskedAnnotation(MaskType.NO_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertEquals("testValue", result);
	}

	/**
	 * 测试姓名脱敏类型
	 */
	@Test
	@DisplayName("测试姓名脱敏类型 - NAME_MASK")
	public void testNameMask() {
		String input = "张三";
		Masked annotation = createMaskedAnnotation(MaskType.NAME_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 测试电话脱敏类型
	 */
	@Test
	@DisplayName("测试电话脱敏类型 - PHONE_MASK")
	public void testPhoneMask() {
		String input = "13812345678";
		Masked annotation = createMaskedAnnotation(MaskType.PHONE_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 测试身份证脱敏类型
	 */
	@Test
	@DisplayName("测试身份证脱敏类型 - IDCARD_MASK")
	public void testIdCardMask() {
		String input = "110101199001011234";
		Masked annotation = createMaskedAnnotation(MaskType.IDCARD_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 测试邮箱脱敏类型
	 */
	@Test
	@DisplayName("测试邮箱脱敏类型 - EMAIL_MASK")
	public void testEmailMask() {
		String input = "test@example.com";
		Masked annotation = createMaskedAnnotation(MaskType.EMAIL_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 测试银行卡脱敏类型
	 */
	@Test
	@DisplayName("测试银行卡脱敏类型 - BANKCARD_MASK")
	public void testBankCardMask() {
		String input = "6222021234567890123";
		Masked annotation = createMaskedAnnotation(MaskType.BANKCARD_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 测试地址脱敏类型
	 */
	@Test
	@DisplayName("测试地址脱敏类型 - ADDRESS_MASK")
	public void testAddressMask() {
		String input = "北京市朝阳区某某街道某某小区";
		Masked annotation = createMaskedAnnotation(MaskType.ADDRESS_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 测试全脱敏类型 - 正常输入
	 */
	@Test
	@DisplayName("测试全脱敏类型 - 正常输入")
	public void testAllMaskWithInput() {
		String input = "testValue";
		Masked annotation = createMaskedAnnotation(MaskType.ALL_MASK, 0, 0, "#");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertEquals("#########", result);
	}

	/**
	 * 测试全脱敏类型 - 空输入
	 */
	@Test
	@DisplayName("测试全脱敏类型 - 空输入")
	public void testAllMaskWithEmptyInput() {
		String input = "";
		Masked annotation = createMaskedAnnotation(MaskType.ALL_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertEquals("", result);
	}

	/**
	 * 测试全脱敏类型 - null输入
	 */
	@Test
	@DisplayName("测试全脱敏类型 - null输入")
	public void testAllMaskWithNullInput() {
		String input = null;
		Masked annotation = createMaskedAnnotation(MaskType.ALL_MASK, 0, 0, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNull(result);
	}

	/**
	 * 测试自定义脱敏类型 - 正常输入
	 */
	@Test
	@DisplayName("测试自定义脱敏类型 - 正常输入")
	public void testCustomMaskWithInput() {
		String input = "1234567890";
		Masked annotation = createMaskedAnnotation(MaskType.CUSTOM_MASK, 2, 6, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertEquals("12*****890", result);
	}

	/**
	 * 测试自定义脱敏类型 - 空输入
	 */
	@Test
	@DisplayName("测试自定义脱敏类型 - 空输入")
	public void testCustomMaskWithEmptyInput() {
		String input = "";
		Masked annotation = createMaskedAnnotation(MaskType.CUSTOM_MASK, 2, 6, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertEquals("", result);
	}

	/**
	 * 测试自定义脱敏类型 - null输入
	 */
	@Test
	@DisplayName("测试自定义脱敏类型 - null输入")
	public void testCustomMaskWithNullInput() {
		String input = null;
		Masked annotation = createMaskedAnnotation(MaskType.CUSTOM_MASK, 2, 6, "*");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNull(result);
	}

	/**
	 * 测试自定义脱敏符号
	 */
	@Test
	@DisplayName("测试自定义脱敏符号")
	public void testCustomMaskSymbol() {
		String input = "13812345678";
		Masked annotation = createMaskedAnnotation(MaskType.PHONE_MASK, 0, 0, "#");
		String result = maskFunctionFactory.maskData(input, annotation);
		assertNotNull(result);
	}

	/**
	 * 创建模拟的Masked注解
	 */
	private Masked createMaskedAnnotation(MaskType type, int start, int end, String symbol) {
		return new Masked() {
			@Override
			public MaskType type() {
				return type;
			}

			@Override
			public int start() {
				return start;
			}

			@Override
			public int end() {
				return end;
			}

			@Override
			public String symbol() {
				return symbol;
			}

			@Override
			public Class<Masked> annotationType() {
				return Masked.class;
			}
		};
	}

}
