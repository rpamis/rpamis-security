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
package com.rpamis.security.core.field;

/**
 * 字段Field处理接口
 *
 * @author benym
 * @since 2023/9/6 14:18
 */
public interface FieldProcess {

	/**
	 * 根据上下文进行field处理
	 * @param processContext 脱敏处理上下文
	 * @throws IllegalAccessException
	 */
	void processField(ProcessContext processContext) throws IllegalAccessException;

}
