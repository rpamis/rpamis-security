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
package com.rpamis.security.core.utils;

import com.rpamis.security.core.factory.MaskFunctionFactory;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.TypeHandler;
import com.rpamis.security.core.field.impl.ArrayTypeHandler;
import com.rpamis.security.core.field.impl.CollectionTypeHandler;
import com.rpamis.security.core.field.impl.MapTypeHandler;
import com.rpamis.security.core.field.impl.OtherTypeHandler;
import com.rpamis.security.core.field.impl.desensitization.DataMaskingProcessor;
import com.rpamis.security.core.field.impl.desensitization.MaskingResponseProcessor;
import com.rpamis.security.core.field.impl.desensitization.NestedMaskingProcessor;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 脱敏注解处理者
 *
 * @author benym
 * @since 2023/9/6 11:37
 */
public class MaskAnnotationResolver {

	private MaskAnnotationResolver() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Filed处理者list
	 */
	private static final List<FieldProcess> PROCESS_LIST = new ArrayList<>();

	/**
	 * 类型处理者list
	 */
	private static final List<TypeHandler> HANDLER_LIST = new ArrayList<>();

	static {
		// 必要处理者初始化
		PROCESS_LIST.add(new MaskingResponseProcessor());
		PROCESS_LIST.add(new NestedMaskingProcessor());
		PROCESS_LIST.add(new DataMaskingProcessor());
		HANDLER_LIST.add(new ArrayTypeHandler());
		HANDLER_LIST.add(new CollectionTypeHandler());
		HANDLER_LIST.add(new MapTypeHandler());
		HANDLER_LIST.add(new OtherTypeHandler());
	}

	/**
	 * 将源对象进行脱敏返回
	 * @param sourceObject 源对象
	 * @return Object
	 */
	@SuppressWarnings("all")
	public static Object processObjectAndMask(Object sourceObject) throws IllegalAccessException {
		if (sourceObject == null) {
			return null;
		}
		Set<Object> referenceSet = Collections.newSetFromMap(new IdentityHashMap<>());
		// 解析队列
		Deque<Object> analyzeDeque = new ArrayDeque<>();
		analyzeDeque.offer(findActualMaskObject(sourceObject));
		MaskFunctionFactory maskFunctionFactory = new MaskFunctionFactory();
		while (!analyzeDeque.isEmpty()) {
			Object currentObj = analyzeDeque.poll();
			Field[] fields = FieldUtils.getAllFields(currentObj);
			for (Field field : fields) {
				field.setAccessible(true);
				// 获取当前对象的对应的Field的值
				Object fieldValue = field.get(currentObj);
				if (null != fieldValue) {
					Class<?> fieldValueClass = fieldValue.getClass();
					ProcessContext processContext = new ProcessContext(currentObj, fieldValue, field, fieldValueClass,
							referenceSet, HANDLER_LIST, analyzeDeque);
					processContext.setMaskFunctionFactory(maskFunctionFactory);
					// 字段解析并脱敏
					for (FieldProcess fieldProcess : PROCESS_LIST) {
						fieldProcess.processField(processContext);
					}
				}
			}
		}
		return sourceObject;
	}

	/**
	 * 是否不是基础类型，或已经解析过
	 * @param clazz 当前Class
	 * @param element 当前对象元素
	 * @param referenceCounter 防重set
	 * @return boolean
	 */
	public static boolean isNotBaseType(Class<?> clazz, Object element, Set<Object> referenceCounter) {
		return !clazz.isPrimitive() && clazz.getPackage() != null && !clazz.isEnum()
				&& !clazz.getPackage().getName().startsWith("java.")
				&& !clazz.getPackage().getName().startsWith("javax.") && !clazz.getName().startsWith("java.")
				&& !clazz.getName().startsWith("javax.") && !clazz.getName().startsWith("jakarta.")
				&& !clazz.getName().startsWith("sun.") && referenceCounter.add(element);
	}

	/**
	 * 找到真正需要Mask的Object，针对HashMap处理 因为HashMap的key value属于静态内部类HashMap.Node
	 * 这个对象判断isArray时为true，单独判断是否为HashMap.Node将无法通过编译 导致后续脱敏处理失效
	 * @param sourceObject 源对象
	 * @return Object
	 */
	private static Object findActualMaskObject(Object sourceObject) {
		if (sourceObject instanceof HashMap) {
			return ((HashMap<?, ?>) sourceObject).values();
		}
		return sourceObject;
	}

}
