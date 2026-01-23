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

    private Pointcut buildPointCut(){
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
