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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Field工具类
 *
 * @author benym
 * @since 2023/9/6 15:42
 */
public class FieldUtils {

	private FieldUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 获取包括父类所有的属性
	 * @param sourceObject 源对象
	 * @return Field[]
	 */
	public static Field[] getAllFields(Object sourceObject) {
		// 获得当前类的所有属性(private、protected、public)
		List<Field> fieldList = new ArrayList<>();
		Class<?> tempClass = sourceObject.getClass();
		String objString = "java.lang.object";
		// 当父类为null的时候说明到达了最上层的父类(Object类).
		while (tempClass != null && !tempClass.getName().toLowerCase().equals(objString)) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

}
