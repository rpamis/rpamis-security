package com.rpamis.security.annotation;

import com.rpamis.security.mask.MaskType;

import java.lang.annotation.*;

/**
 * 脱敏字段注解
 *
 * @author benym
 * @date 2023/9/18 23:12
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Masked {

    /**
     * 脱敏类型
     *
     * @return MaskType
     */
    MaskType type() default MaskType.NO_MASK;

    /**
     * 脱敏开始位置，仅在MaskTypeFunction.CUSTOM_MASK下生效
     *
     * @return String
     */
    int start() default 0;

    /**
     * 脱敏结束位置，仅在MaskTypeFunction.CUSTOM_MASK下生效
     *
     * @return String
     */
    int end() default 0;

    /**
     * 脱敏符号
     *
     * @return String
     */
    String symbol() default "*";
}
