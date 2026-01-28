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

import com.rpamis.security.core.utils.StringReplaceUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StringReplaceUtils 测试类
 *
 * @author benym
 * @since 2026/1/28
 */
public class StringReplaceUtilsTest {

	/**
	 * 测试有效参数的字符串替换 验证从索引2到6的字符被替换为*
	 */
	@Test
	@DisplayName("测试有效参数的字符串替换")
	public void testReplaceWithValidParameters() {
		String source = "1234567890";
		String result = StringReplaceUtils.replace(source, 2, 6, "*");
		assertEquals("12*****890", result);
	}

	/**
	 * 测试起始位置大于结束位置的情况 验证当start > end时，返回原始字符串
	 */
	@Test
	@DisplayName("测试起始位置大于结束位置的情况")
	public void testReplaceWithStartGreaterThanEnd() {
		String source = "1234567890";
		String result = StringReplaceUtils.replace(source, 6, 2, "*");
		assertEquals(source, result);
	}

	/**
	 * 测试起始位置等于结束位置的情况 验证当start == end时，返回原始字符串
	 */
	@Test
	@DisplayName("测试起始位置等于结束位置的情况")
	public void testReplaceWithStartEqualToEnd() {
		String source = "1234567890";
		String result = StringReplaceUtils.replace(source, 5, 5, "*");
		assertEquals(source, result);
	}

	/**
	 * 测试起始位置超出边界的情况 验证当start超出字符串长度时，返回原始字符串
	 */
	@Test
	@DisplayName("测试起始位置超出边界的情况")
	public void testReplaceWithStartOutOfBounds() {
		String source = "1234567890";
		String result = StringReplaceUtils.replace(source, 10, 15, "*");
		assertEquals(source, result);
	}

	/**
	 * 测试负参数的情况 验证当start为负数时，返回原始字符串
	 */
	@Test
	@DisplayName("测试负参数的情况")
	public void testReplaceWithNegativeParameters() {
		String source = "1234567890";
		String result = StringReplaceUtils.replace(source, -1, 5, "*");
		assertEquals(source, result);
	}

	/**
	 * 测试空字符串的情况 验证当源字符串为空时，返回原始字符串
	 */
	@Test
	@DisplayName("测试空字符串的情况")
	public void testReplaceWithEmptySource() {
		String source = "";
		String result = StringReplaceUtils.replace(source, 0, 1, "*");
		assertEquals(source, result);
	}

	/**
	 * 测试替换所有字符 验证将字符串中的所有字符替换为*
	 */
	@Test
	@DisplayName("测试替换所有字符")
	public void testReplaceEveryWithValidSource() {
		String source = "12345";
		String result = StringReplaceUtils.replaceEvery(source, "*");
		assertEquals("*****", result);
	}

	/**
	 * 测试空字符串替换所有字符 验证当源字符串为空时，返回空字符串
	 */
	@Test
	@DisplayName("测试空字符串替换所有字符")
	public void testReplaceEveryWithEmptySource() {
		String source = "";
		String result = StringReplaceUtils.replaceEvery(source, "*");
		assertEquals("", result);
	}

	/**
	 * 测试私有构造函数 验证通过反射调用私有构造函数会抛出异常
	 */
	@Test
	@DisplayName("测试私有构造函数")
	public void testPrivateConstructorThrowsException() {
		assertThrows(InvocationTargetException.class, () -> {
			// 使用反射创建实例
			java.lang.reflect.Constructor<StringReplaceUtils> constructor = StringReplaceUtils.class
				.getDeclaredConstructor();
			constructor.setAccessible(true);
			constructor.newInstance();
		});
	}

}
