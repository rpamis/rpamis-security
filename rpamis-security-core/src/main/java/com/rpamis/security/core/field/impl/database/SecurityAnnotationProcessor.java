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
package com.rpamis.security.core.field.impl.database;

import com.rpamis.security.annotation.SecurityField;
import com.rpamis.security.core.field.FieldProcess;
import com.rpamis.security.core.field.ProcessContext;
import com.rpamis.security.core.utils.SecurityUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * SecurityField注解解密处理
 *
 * @author benym
 * @since 2026/1/7 18:14
 */
public class SecurityAnnotationProcessor implements FieldProcess {

	private final SecurityUtils securityUtils;

	public SecurityAnnotationProcessor(SecurityUtils securityUtils) {
		this.securityUtils = securityUtils;
	}

	@Override
	public void processField(ProcessContext processContext) throws IllegalAccessException {
		Field field = processContext.getField();
		Object fieldValue = processContext.getFieldValue();
		Object currentObject = processContext.getCurrentObject();
		if (field.isAnnotationPresent(SecurityField.class) && fieldValue instanceof String) {
			String stringObject = String.valueOf(fieldValue);
			String decryptedString = securityUtils.decryptWithPrefix(stringObject);
			ReflectionUtils.setField(field, currentObject, decryptedString);
		}
	}

}
