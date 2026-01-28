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

import com.rpamis.security.core.mybatisplus.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;

/**
 * Constants 测试类
 *
 * @author benym
 * @since 2026/1/28
 */
public class ConstantsTest {

	/**
	 * 测试 Constants.ENTITY 常量值
	 */
	@Test
	@DisplayName("测试 Constants.ENTITY 常量值")
	public void testEntityConstantValue() {
		assertEquals("et", Constants.ENTITY);
	}

	/**
	 * 测试 Constants 私有构造函数抛出异常
	 */
	@Test
	@DisplayName("测试 Constants 私有构造函数抛出异常")
	public void testPrivateConstructorThrowsException() {
		assertThrows(InvocationTargetException.class, () -> {
			// 使用反射创建实例
			java.lang.reflect.Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			constructor.newInstance();
		});
	}

}
