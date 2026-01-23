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
package com.rpamis.security.annotation;

import com.rpamis.security.mask.MaskType;

import java.lang.annotation.*;

/**
 * 脱敏字段注解
 *
 * @author benym
 * @since 2023/9/18 23:12
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Masked {

	/**
	 * 脱敏类型
	 * @return MaskType
	 */
	MaskType type() default MaskType.NO_MASK;

	/**
	 * 脱敏开始位置，仅在MaskTypeFunction.CUSTOM_MASK下生效
	 * @return String
	 */
	int start() default 0;

	/**
	 * 脱敏结束位置，仅在MaskTypeFunction.CUSTOM_MASK下生效
	 * @return String
	 */
	int end() default 0;

	/**
	 * 脱敏符号
	 * @return String
	 */
	String symbol() default "*";

}
