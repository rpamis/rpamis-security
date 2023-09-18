package com.rpamis.security.annotation;

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

}
