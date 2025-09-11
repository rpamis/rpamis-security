package com.rpamis.security.annotation;

import java.lang.annotation.*;

/**
 * 脱敏注解，用于标记脱敏方法
 *
 * @author benym
 * @date 2023/9/18 23:08
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Desensitizationed {

}
