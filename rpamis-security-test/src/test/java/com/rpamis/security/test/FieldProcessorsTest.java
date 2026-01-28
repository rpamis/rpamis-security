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
import com.rpamis.security.annotation.SecurityField;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.impl.database.FinderSecurityProcessor;
import com.rpamis.security.core.field.impl.database.SecurityAnnotationProcessor;
import com.rpamis.security.core.field.impl.desensitization.DataMaskingProcessor;
import com.rpamis.security.core.factory.MaskFunctionFactory;
import com.rpamis.security.core.properties.Algorithm;
import com.rpamis.security.core.properties.DefaultPrefix;
import com.rpamis.security.core.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * 字段处理器测试类
 *
 * @author benym
 * @since 2026/1/28
 */
public class FieldProcessorsTest {

	private SecurityUtils securityUtils;

	private MaskFunctionFactory maskFunctionFactory;

	private ProcessContext processContext;

	@BeforeEach
	public void setUp() {
		securityUtils = Mockito.mock(SecurityUtils.class);
		maskFunctionFactory = Mockito.mock(MaskFunctionFactory.class);
		processContext = Mockito.mock(ProcessContext.class);
	}

	/**
	 * 测试带有@SecurityField注解的字段处理 验证SecurityAnnotationProcessor能够正确解密带有@SecurityField注解的字段值
	 */
	@Test
	@DisplayName("测试带有@SecurityField注解的字段处理")
	public void testSecurityAnnotationProcessorWithSecurityField() throws IllegalAccessException, NoSuchFieldException {
		// 准备测试数据
		TestEntity testEntity = new TestEntity();
		testEntity.setSecureField(DefaultPrefix.SM4.getPrefix() + "encryptedValue");
		Field field = TestEntity.class.getDeclaredField("secureField");
		field.setAccessible(true);

		// 模拟方法调用
		when(processContext.getField()).thenReturn(field);
		when(processContext.getFieldValue()).thenReturn(testEntity.getSecureField());
		when(processContext.getCurrentObject()).thenReturn(testEntity);
		when(securityUtils.decryptWithPrefix(DefaultPrefix.SM4.getPrefix() + "encryptedValue"))
			.thenReturn("decryptedValue");

		// 执行测试
		FieldProcess processor = new SecurityAnnotationProcessor(securityUtils);
		processor.processField(processContext);

		// 验证结果
		assertEquals("decryptedValue", testEntity.getSecureField());
	}

	/**
	 * 测试不带@SecurityField注解的字段处理 验证SecurityAnnotationProcessor对不带@SecurityField注解的字段不做处理
	 */
	@Test
	@DisplayName("测试不带@SecurityField注解的字段处理")
	public void testSecurityAnnotationProcessorWithoutSecurityField()
			throws IllegalAccessException, NoSuchFieldException {
		// 准备测试数据
		TestEntity testEntity = new TestEntity();
		testEntity.setNonSecureField("value");
		Field field = TestEntity.class.getDeclaredField("nonSecureField");
		field.setAccessible(true);

		// 模拟方法调用
		when(processContext.getField()).thenReturn(field);
		when(processContext.getFieldValue()).thenReturn(testEntity.getNonSecureField());
		when(processContext.getCurrentObject()).thenReturn(testEntity);

		// 执行测试
		FieldProcess processor = new SecurityAnnotationProcessor(securityUtils);
		processor.processField(processContext);

		// 验证结果 - 无变化
		assertEquals("value", testEntity.getNonSecureField());
	}

	/**
	 * 测试非字符串值的字段处理 验证SecurityAnnotationProcessor对非字符串值的字段不做处理
	 */
	@Test
	@DisplayName("测试非字符串值的字段处理")
	public void testSecurityAnnotationProcessorWithNonStringValue()
			throws IllegalAccessException, NoSuchFieldException {
		// 准备测试数据
		TestEntity testEntity = new TestEntity();
		testEntity.setSecureField(DefaultPrefix.SM4.getPrefix() + "encryptedValue");
		Field field = TestEntity.class.getDeclaredField("secureField");
		field.setAccessible(true);

		// 模拟方法调用 - 字段值为非字符串
		when(processContext.getField()).thenReturn(field);
		when(processContext.getFieldValue()).thenReturn(123);
		when(processContext.getCurrentObject()).thenReturn(testEntity);

		// 执行测试
		FieldProcess processor = new SecurityAnnotationProcessor(securityUtils);
		processor.processField(processContext);

		// 验证结果 - 无变化
		assertEquals(DefaultPrefix.SM4.getPrefix() + "encryptedValue", testEntity.getSecureField());
	}

	/**
	 * 测试带有@Masked注解的字段处理 验证DataMaskingProcessor能够正确对带有@Masked注解的字段值进行脱敏处理
	 */
	@Test
	@DisplayName("测试带有@Masked注解的字段处理")
	public void testDataMaskingProcessorWithMaskedAnnotation() throws IllegalAccessException, NoSuchFieldException {
		// 准备测试数据
		TestEntity testEntity = new TestEntity();
		testEntity.setMaskedField("testValue");
		Field field = TestEntity.class.getDeclaredField("maskedField");
		field.setAccessible(true);

		// 模拟方法调用
		when(processContext.getField()).thenReturn(field);
		when(processContext.getFieldValue()).thenReturn(testEntity.getMaskedField());
		when(processContext.getCurrentObject()).thenReturn(testEntity);
		when(processContext.getMaskFunctionFactory()).thenReturn(maskFunctionFactory);
		when(maskFunctionFactory.maskData("testValue", field.getAnnotation(Masked.class))).thenReturn("maskedValue");

		// 执行测试
		FieldProcess processor = new DataMaskingProcessor();
		processor.processField(processContext);

		// 验证结果
		assertEquals("maskedValue", testEntity.getMaskedField());
	}

	/**
	 * 测试不带@Masked注解的字段处理 验证DataMaskingProcessor对不带@Masked注解的字段不做处理
	 */
	@Test
	@DisplayName("测试不带@Masked注解的字段处理")
	public void testDataMaskingProcessorWithoutMaskedAnnotation() throws IllegalAccessException, NoSuchFieldException {
		// 准备测试数据
		TestEntity testEntity = new TestEntity();
		testEntity.setNonMaskedField("value");
		Field field = TestEntity.class.getDeclaredField("nonMaskedField");
		field.setAccessible(true);

		// 模拟方法调用
		when(processContext.getField()).thenReturn(field);
		when(processContext.getFieldValue()).thenReturn(testEntity.getNonMaskedField());
		when(processContext.getCurrentObject()).thenReturn(testEntity);

		// 执行测试
		FieldProcess processor = new DataMaskingProcessor();
		processor.processField(processContext);

		// 验证结果 - 无变化
		assertEquals("value", testEntity.getNonMaskedField());
	}

	/**
	 * 测试FinderSecurityProcessor 验证FinderSecurityProcessor能够正确调用TypeHandler的handle方法
	 */
	@Test
	@DisplayName("测试FinderSecurityProcessor")
	public void testFinderSecurityProcessor() throws IllegalAccessException, NoSuchFieldException {
		// 准备测试数据
		TestEntity testEntity = new TestEntity();
		testEntity.setNonSecureField("value");
		Field field = TestEntity.class.getDeclaredField("nonSecureField");
		field.setAccessible(true);

		// 创建模拟的TypeHandler
		TestTypeHandler testTypeHandler = Mockito.mock(TestTypeHandler.class);
		when(testTypeHandler.handle(processContext)).thenReturn(true);

		List<TestTypeHandler> handlerList = new ArrayList<>();
		handlerList.add(testTypeHandler);

		// 模拟方法调用
		when(processContext.getHandlerList()).thenReturn((List) handlerList);

		// 执行测试
		FieldProcess processor = new FinderSecurityProcessor();
		processor.processField(processContext);

		// 验证结果 - 验证TypeHandler.handle方法被调用
		Mockito.verify(testTypeHandler, Mockito.times(1)).handle(processContext);
	}

	/**
	 * 测试实体类
	 */
	private static class TestEntity {

		@SecurityField
		private String secureField;

		private String nonSecureField;

		@Masked
		private String maskedField;

		private String nonMaskedField;

		public String getSecureField() {
			return secureField;
		}

		public void setSecureField(String secureField) {
			this.secureField = secureField;
		}

		public String getNonSecureField() {
			return nonSecureField;
		}

		public void setNonSecureField(String nonSecureField) {
			this.nonSecureField = nonSecureField;
		}

		public String getMaskedField() {
			return maskedField;
		}

		public void setMaskedField(String maskedField) {
			this.maskedField = maskedField;
		}

		public String getNonMaskedField() {
			return nonMaskedField;
		}

		public void setNonMaskedField(String nonMaskedField) {
			this.nonMaskedField = nonMaskedField;
		}

	}

	/**
	 * 测试TypeHandler接口
	 */
	private interface TestTypeHandler extends com.rpamis.security.core.field.TypeHandler {

	}

}
