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
package com.rpamis.security.core.field.impl.desensitization;

import com.rpamis.security.annotation.Masked;
import com.rpamis.security.core.factory.MaskFunctionFactory;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;

import java.lang.reflect.Field;

/**
 * DataMasking脱敏注解处理 只能处理实体为非泛型的，实体内可带有其余泛型 比如DemoUser可以处理, DemoUser内的List<DemoUser>也可以处理
 * 但对于直接的List<DemoUser>无法处理，因为无法知道泛型内对象到底是什么，取哪个字段 对于这样的泛型情况统一由MaskingResponseProcessor处理
 *
 * @see MaskingResponseProcessor
 * @author benym
 * @since 2023/9/6 14:30
 */
public class DataMaskingProcessor implements FieldProcess {

	@Override
	@SuppressWarnings("all")
	public void processField(ProcessContext processContext) throws IllegalAccessException {
		Field field = processContext.getField();
		Object fieldValue = processContext.getFieldValue();
		Object currentObject = processContext.getCurrentObject();
		if (field.isAnnotationPresent(Masked.class)) {
			Masked annotation = field.getAnnotation(Masked.class);
			MaskFunctionFactory maskFunctionFactory = processContext.getMaskFunctionFactory();
			String maskValue = maskFunctionFactory.maskData(String.valueOf(fieldValue), annotation);
			field.set(currentObject, maskValue);
		}
	}

}
