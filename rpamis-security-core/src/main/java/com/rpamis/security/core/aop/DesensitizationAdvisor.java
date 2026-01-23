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

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.StringUtils;

/**
 * 脱敏Advisor
 *
 * @author benym
 * @since 2023/9/4 16:39
 */
public class DesensitizationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

	private static final long serialVersionUID = -7279179967128662308L;

	private static final String DEFAULT_POINTCUT = "@annotation(com.rpamis.security.annotation.Desensitizationed)";

	@SuppressWarnings("all")
	private final Advice advice;

	@SuppressWarnings("all")
	private final Pointcut pointcut;

	private final String customPointcut;

	public DesensitizationAdvisor(DesensitizationInterceptor desensitizationInterceptor, String customPointcut) {
		this.advice = desensitizationInterceptor;
		this.customPointcut = customPointcut;
		this.pointcut = buildPointCut();
	}

	private Pointcut buildPointCut() {
		AspectJExpressionPointcut ajpc = new AspectJExpressionPointcut();
		ajpc.setExpression(buildCutExpression(customPointcut));
		return ajpc;
	}

	private String buildCutExpression(String customPointcut) {
		StringBuilder sbf = new StringBuilder(DEFAULT_POINTCUT);
		if (!StringUtils.isEmpty(customPointcut)) {
			sbf.append(" || ").append(customPointcut);
		}
		return sbf.toString();
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (advice instanceof BeanFactoryAware) {
			((BeanFactoryAware) advice).setBeanFactory(beanFactory);
		}
	}

}
