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
package com.rpamis.security.core.field.impl;

import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.TypeHandler;
import com.rpamis.security.core.utils.MaskAnnotationResolver;

import java.lang.reflect.Array;
import java.util.Deque;
import java.util.Set;

/**
 * 处理Array数据类型
 *
 * @author benym
 * @since 2023/9/6 17:01
 */
public class ArrayTypeHandler implements TypeHandler {

	@Override
	public boolean handle(ProcessContext processContext) {
		Class<?> fieldValueClass = processContext.getFieldValueClass();
		Object fieldValue = processContext.getFieldValue();
		Set<Object> referenceSet = processContext.getReferenceSet();
		Deque<Object> analyzeDeque = processContext.getAnalyzeDeque();
		if (fieldValueClass.isArray()) {
			int length = Array.getLength(fieldValue);
			for (int i = 0; i < length; i++) {
				Object arrayObject = Array.get(fieldValue, i);
				if (null != arrayObject
						&& MaskAnnotationResolver.isNotBaseType(arrayObject.getClass(), arrayObject, referenceSet)) {
					analyzeDeque.offer(arrayObject);
				}
			}
			return true;
		}
		return false;
	}

}
