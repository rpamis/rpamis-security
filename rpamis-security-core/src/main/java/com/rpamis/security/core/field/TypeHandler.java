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
 * 类型处理器接口
 *
 * @author benym
 * @since 2023/9/6 16:59
 */
public interface TypeHandler {

	/**
	 * 根据处理上下文进行类型处理
	 * @param processContext processContext
	 * @return boolean
	 */
	boolean handle(ProcessContext processContext);

}
