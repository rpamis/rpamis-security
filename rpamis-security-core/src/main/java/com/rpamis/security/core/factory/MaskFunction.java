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
package com.rpamis.security.core.factory;

import com.rpamis.security.annotation.Masked;

/**
 * 脱敏函数接口
 *
 * @author benym
 * @since 2023/10/8 17:26
 */
@FunctionalInterface
public interface MaskFunction {

	/**
	 * 脱敏
	 * @param unMaskInput unMaskInput
	 * @param annotation Masked注解
	 * @return String
	 */
	String mask(String unMaskInput, Masked annotation);

}
