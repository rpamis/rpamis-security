package com.rpamis.security.annotation;

import java.lang.annotation.*;

/**
 * 加解密字段注解
 *
 * @author benym
 * @since 2023/9/18 23:10
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityField {

}
