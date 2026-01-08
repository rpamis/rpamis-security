package com.rpamis.security.core.aop;

import com.rpamis.security.core.utils.MaskAnnotationResolver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 脱敏切面
 *
 * @author benym
 * @date 2023/9/4 15:59
 */
public class DesensitizationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesensitizationAspect.class);

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed(args);
        try {
            result = MaskAnnotationResolver.processObjectAndMask(result);
        } catch (Exception e) {
            LOGGER.error("DesensitizationAspect process unknown error", e);
        }
        return result;
    }
}
