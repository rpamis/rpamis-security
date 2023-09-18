package com.rpamis.security.annotation;

import java.lang.annotation.*;

/**
 * 嵌套脱敏字段注解
 *
 * @author benym
 * @date 2023/9/18 23:16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NestedMasked {

}
