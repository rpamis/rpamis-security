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
