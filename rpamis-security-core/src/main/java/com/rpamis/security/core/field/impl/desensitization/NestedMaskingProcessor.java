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

import com.rpamis.security.annotation.NestedMasked;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.TypeHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 嵌套脱敏注解处理器 只能处理实体为非泛型的，实体内可带有其余泛型 比如DemoUser可以处理, DemoUser内的List<DemoUser>也可以处理
 * 但对于直接的List<DemoUser>无法处理，因为无法知道泛型内对象到底是什么，取哪个字段 对于这样的泛型情况统一由MaskingResponseProcessor处理
 *
 * @author benym
 * @since 2023/9/6 14:31
 * @see MaskingResponseProcessor
 */
public class NestedMaskingProcessor implements FieldProcess {

	@Override
	public void processField(ProcessContext processContext) {
		Field field = processContext.getField();
		List<TypeHandler> handlerList = processContext.getHandlerList();
		// 如果字段被标记为需要嵌套脱敏
		if (field.isAnnotationPresent(NestedMasked.class)) {
			for (TypeHandler handler : handlerList) {
				boolean handleResult = handler.handle(processContext);
				if (handleResult) {
					break;
				}
			}
		}
	}

}
