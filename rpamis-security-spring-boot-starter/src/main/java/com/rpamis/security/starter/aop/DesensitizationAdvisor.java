package com.rpamis.security.starter.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 脱敏Advisor
 *
 * @author benym
 * @date 2023/9/4 16:39
 */
public class DesensitizationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private static final long serialVersionUID = -7279179967128662308L;

    private static final String DEFAULT_POINTCUT = "@annotation(com.rpamis.security.annotation.Desensitizationed)";

    @SuppressWarnings("all")
    private final Advice advice;

    @SuppressWarnings("all")
    private final Pointcut pointcut;

    public DesensitizationAdvisor(DesensitizationInterceptor desensitizationInterceptor) {
        this.advice = desensitizationInterceptor;
        this.pointcut = buildPointCut();
    }

    private Pointcut buildPointCut(){
        AspectJExpressionPointcut ajpc = new AspectJExpressionPointcut();
        ajpc.setExpression(DEFAULT_POINTCUT);
        return ajpc;
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
