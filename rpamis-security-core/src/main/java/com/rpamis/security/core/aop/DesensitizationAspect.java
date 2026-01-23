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
package com.rpamis.security.core.aop;

import com.rpamis.security.core.utils.MaskAnnotationResolver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 脱敏切面
 *
 * @author benym
 * @since 2023/9/4 15:59
 */
public class DesensitizationAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesensitizationAspect.class);

	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		Object result = joinPoint.proceed(args);
		try {
			result = MaskAnnotationResolver.processObjectAndMask(result);
		}
		catch (Exception e) {
			LOGGER.error("DesensitizationAspect process unknown error", e);
		}
		return result;
	}

}
