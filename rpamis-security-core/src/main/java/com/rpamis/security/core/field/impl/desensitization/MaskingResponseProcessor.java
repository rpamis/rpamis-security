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

import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.field.TypeHandler;

import java.util.List;

/**
 * 脱敏基础返回值处理器，用于所有带泛型的对象 比如List<>,Map<>,Collections<>,自定义返回体
 *
 * @author benym
 * @since 2023/9/6 16:35
 */
public class MaskingResponseProcessor implements FieldProcess {

	@Override
	public void processField(ProcessContext processContext) throws IllegalAccessException {
		List<TypeHandler> handlerList = processContext.getHandlerList();
		for (TypeHandler handler : handlerList) {
			boolean handleResult = handler.handle(processContext);
			if (handleResult) {
				break;
			}
		}
	}

}
