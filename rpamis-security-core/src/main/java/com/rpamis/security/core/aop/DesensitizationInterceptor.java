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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;

/**
 * 脱敏增强Interceptor
 *
 * @author benym
 * @since 2023/9/4 16:19
 */
public class DesensitizationInterceptor implements MethodInterceptor {

	private final DesensitizationAspect desensitizationAspect;

	public DesensitizationInterceptor(DesensitizationAspect desensitizationAspect) {
		this.desensitizationAspect = desensitizationAspect;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!(methodInvocation instanceof ProxyMethodInvocation)) {
			throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation");
		}
		ProxyMethodInvocation pmi = (ProxyMethodInvocation) methodInvocation;
		ProceedingJoinPoint pjp = lazyInitJoinPoint(pmi);
		return doAroud(pjp);
	}

	private ProceedingJoinPoint lazyInitJoinPoint(ProxyMethodInvocation pmi) {
		return new MethodInvocationProceedingJoinPoint(pmi);
	}

	private Object doAroud(ProceedingJoinPoint pjp) throws Throwable {
		return desensitizationAspect.around(pjp);
	}

}
