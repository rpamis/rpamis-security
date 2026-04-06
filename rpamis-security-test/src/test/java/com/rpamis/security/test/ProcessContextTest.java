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

import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.TypeHandler;
import com.rpamis.security.core.factory.MaskFunctionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ProcessContext单元测试
 *
 * @author benym
 * @since 2026/4/6
 */
public class ProcessContextTest {

	/**
	 * 测试默认构造函数
	 */
	@Test
	@DisplayName("测试默认构造函数")
	public void testDefaultConstructor() {
		ProcessContext context = new ProcessContext();
		assertNull(context.getCurrentObject());
		assertNull(context.getFieldValue());
		assertNull(context.getField());
		assertNull(context.getFieldValueClass());
		assertNull(context.getReferenceSet());
		assertNull(context.getHandlerList());
		assertNull(context.getAnalyzeDeque());
		assertNull(context.getMaskFunctionFactory());
	}

	/**
	 * 测试带参数构造函数
	 */
	@Test
	@DisplayName("测试带参数构造函数")
	public void testParameterizedConstructor() throws NoSuchFieldException {
		Object currentObject = new Object();
		Object fieldValue = "testValue";
		Field field = String.class.getDeclaredField("value");
		Class<?> fieldValueClass = String.class;
		Set<Object> referenceSet = new HashSet<>();
		List<TypeHandler> handlerList = new ArrayList<>();
		ArrayDeque<Object> analyzeDeque = new ArrayDeque<>();

		ProcessContext context = new ProcessContext(currentObject, fieldValue, field, fieldValueClass, referenceSet,
				handlerList, analyzeDeque);

		assertEquals(currentObject, context.getCurrentObject());
		assertEquals(fieldValue, context.getFieldValue());
		assertEquals(field, context.getField());
		assertEquals(fieldValueClass, context.getFieldValueClass());
		assertEquals(referenceSet, context.getReferenceSet());
		assertEquals(handlerList, context.getHandlerList());
		assertEquals(analyzeDeque, context.getAnalyzeDeque());
	}

	/**
	 * 测试setCurrentObject和getCurrentObject方法
	 */
	@Test
	@DisplayName("测试setCurrentObject和getCurrentObject方法")
	public void testSetGetCurrentObject() {
		ProcessContext context = new ProcessContext();
		Object testObject = new Object();
		context.setCurrentObject(testObject);
		assertEquals(testObject, context.getCurrentObject());
	}

	/**
	 * 测试setFieldValue和getFieldValue方法
	 */
	@Test
	@DisplayName("测试setFieldValue和getFieldValue方法")
	public void testSetGetFieldValue() {
		ProcessContext context = new ProcessContext();
		Object fieldValue = "testFieldValue";
		context.setFieldValue(fieldValue);
		assertEquals(fieldValue, context.getFieldValue());
	}

	/**
	 * 测试setField和getField方法
	 */
	@Test
	@DisplayName("测试setField和getField方法")
	public void testSetGetField() throws NoSuchFieldException {
		ProcessContext context = new ProcessContext();
		Field field = String.class.getDeclaredField("value");
		context.setField(field);
		assertEquals(field, context.getField());
	}

	/**
	 * 测试setFieldValueClass和getFieldValueClass方法
	 */
	@Test
	@DisplayName("测试setFieldValueClass和getFieldValueClass方法")
	public void testSetGetFieldValueClass() {
		ProcessContext context = new ProcessContext();
		Class<?> fieldValueClass = String.class;
		context.setFieldValueClass(fieldValueClass);
		assertEquals(fieldValueClass, context.getFieldValueClass());
	}

	/**
	 * 测试setReferenceSet和getReferenceSet方法
	 */
	@Test
	@DisplayName("测试setReferenceSet和getReferenceSet方法")
	public void testSetGetReferenceSet() {
		ProcessContext context = new ProcessContext();
		Set<Object> referenceSet = new HashSet<>();
		referenceSet.add(new Object());
		context.setReferenceSet(referenceSet);
		assertEquals(referenceSet, context.getReferenceSet());
	}

	/**
	 * 测试setAnalyzeDeque和getAnalyzeDeque方法
	 */
	@Test
	@DisplayName("测试setAnalyzeDeque和getAnalyzeDeque方法")
	public void testSetGetAnalyzeDeque() {
		ProcessContext context = new ProcessContext();
		ArrayDeque<Object> analyzeDeque = new ArrayDeque<>();
		analyzeDeque.push(new Object());
		context.setAnalyzeDeque(analyzeDeque);
		assertEquals(analyzeDeque, context.getAnalyzeDeque());
	}

	/**
	 * 测试setHandlerList和getHandlerList方法
	 */
	@Test
	@DisplayName("测试setHandlerList和getHandlerList方法")
	public void testSetGetHandlerList() {
		ProcessContext context = new ProcessContext();
		List<TypeHandler> handlerList = new ArrayList<>();
		context.setHandlerList(handlerList);
		assertEquals(handlerList, context.getHandlerList());
	}

	/**
	 * 测试setMaskFunctionFactory和getMaskFunctionFactory方法
	 */
	@Test
	@DisplayName("测试setMaskFunctionFactory和getMaskFunctionFactory方法")
	public void testSetGetMaskFunctionFactory() {
		ProcessContext context = new ProcessContext();
		MaskFunctionFactory factory = new MaskFunctionFactory();
		context.setMaskFunctionFactory(factory);
		assertNotNull(context.getMaskFunctionFactory());
	}

}
