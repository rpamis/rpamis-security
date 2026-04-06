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

import com.rpamis.security.core.mybatis.MybatisEncryptInterceptor;
import com.rpamis.security.core.mybatisplus.Constants;
import com.rpamis.security.core.utils.SecurityResolver;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * MybatisEncryptInterceptor单元测试
 *
 * @author benym
 * @since 2026/4/6
 */
public class MybatisEncryptInterceptorTest {

	private SecurityResolver securityResolver;

	private MybatisEncryptInterceptor interceptor;

	@BeforeEach
	public void setUp() {
		securityResolver = mock(SecurityResolver.class);
		interceptor = new MybatisEncryptInterceptor(securityResolver);
	}

	/**
	 * 测试plugin方法
	 */
	@Test
	@DisplayName("测试plugin方法")
	public void testPlugin() {
		ParameterHandler target = mock(ParameterHandler.class);
		Object result = interceptor.plugin(target);
		assertNotNull(result);
	}

	/**
	 * 测试非ParameterHandler类型的target直接proceed
	 */
	@Test
	@DisplayName("测试非ParameterHandler类型的target直接proceed")
	public void testInterceptWithNonParameterHandler() throws Throwable {
		Invocation invocation = mock(Invocation.class);
		when(invocation.getTarget()).thenReturn(new Object());
		when(invocation.proceed()).thenReturn("result");

		Object result = interceptor.intercept(invocation);
		assertEquals("result", result);
	}

	/**
	 * 测试SELECT命令直接proceed
	 */
	@Test
	@DisplayName("测试SELECT命令直接proceed")
	public void testInterceptWithSelectCommand() throws Throwable {
		ParameterHandler parameterHandler = mock(ParameterHandler.class);
		MappedStatement mappedStatement = mock(MappedStatement.class);
		when(mappedStatement.getSqlCommandType()).thenReturn(SqlCommandType.SELECT);

		Invocation invocation = createMockInvocation(parameterHandler, mappedStatement, null);
		when(invocation.proceed()).thenReturn("selectResult");

		Object result = interceptor.intercept(invocation);
		assertEquals("selectResult", result);
	}

	/**
	 * 测试parameterObject为null直接proceed
	 */
	@Test
	@DisplayName("测试parameterObject为null直接proceed")
	public void testInterceptWithNullParameterObject() throws Throwable {
		ParameterHandler parameterHandler = mock(ParameterHandler.class);
		MappedStatement mappedStatement = mock(MappedStatement.class);
		when(mappedStatement.getSqlCommandType()).thenReturn(SqlCommandType.INSERT);
		when(parameterHandler.getParameterObject()).thenReturn(null);

		Invocation invocation = createMockInvocation(parameterHandler, mappedStatement, null);
		when(invocation.proceed()).thenReturn("insertResult");

		Object result = interceptor.intercept(invocation);
		assertEquals("insertResult", result);
	}

	/**
	 * 测试Map类型参数不包含ENTITY key
	 */
	@Test
	@DisplayName("测试Map类型参数不包含ENTITY key")
	public void testInterceptWithMapWithoutEntity() throws Throwable {
		ParameterHandler parameterHandler = mock(ParameterHandler.class);
		MappedStatement mappedStatement = mock(MappedStatement.class);
		when(mappedStatement.getSqlCommandType()).thenReturn(SqlCommandType.INSERT);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("otherKey", "otherValue");
		when(parameterHandler.getParameterObject()).thenReturn(paramMap);

		Invocation invocation = createMockInvocation(parameterHandler, mappedStatement, null);
		when(invocation.proceed()).thenReturn("insertResult");

		Object result = interceptor.intercept(invocation);
		assertEquals("insertResult", result);
	}

	/**
	 * 测试Map类型参数包含ENTITY key但entity为null
	 */
	@Test
	@DisplayName("测试Map类型参数包含ENTITY key但entity为null")
	public void testInterceptWithMapEntityNull() throws Throwable {
		ParameterHandler parameterHandler = mock(ParameterHandler.class);
		MappedStatement mappedStatement = mock(MappedStatement.class);
		when(mappedStatement.getSqlCommandType()).thenReturn(SqlCommandType.INSERT);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(Constants.ENTITY, null);
		when(parameterHandler.getParameterObject()).thenReturn(paramMap);

		Invocation invocation = createMockInvocation(parameterHandler, mappedStatement, null);
		when(invocation.proceed()).thenReturn("insertResult");

		Object result = interceptor.intercept(invocation);
		assertEquals("insertResult", result);
	}

	/**
	 * 测试InvocationTargetException直接抛出
	 */
	@Test
	@DisplayName("测试InvocationTargetException直接抛出")
	public void testInterceptWithInvocationTargetException() throws Throwable {
		ParameterHandler parameterHandler = mock(ParameterHandler.class);
		MappedStatement mappedStatement = mock(MappedStatement.class);
		when(mappedStatement.getSqlCommandType()).thenReturn(SqlCommandType.SELECT);

		Invocation invocation = createMockInvocation(parameterHandler, mappedStatement, null);
		when(invocation.proceed()).thenThrow(new InvocationTargetException(new RuntimeException("test")));

		assertThrows(InvocationTargetException.class, () -> interceptor.intercept(invocation));
	}

	/**
	 * 创建模拟的Invocation对象
	 */
	private Invocation createMockInvocation(ParameterHandler parameterHandler, MappedStatement mappedStatement,
			Object parameterObject) {
		Invocation invocation = mock(Invocation.class);
		when(invocation.getTarget()).thenReturn(parameterHandler);

		try (MockedStatic<SystemMetaObject> mockedStatic = mockStatic(SystemMetaObject.class)) {
			MetaObject metaObject = mock(MetaObject.class);
			when(metaObject.getValue("mappedStatement")).thenReturn(mappedStatement);
			mockedStatic.when(() -> SystemMetaObject.forObject(any())).thenReturn(metaObject);
		}

		return invocation;
	}

}
