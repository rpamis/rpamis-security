package com.rpamis.security.core.mybatisplus;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;

/**
 * mybatis-plus插件工具类
 * 拷贝自
 * @see com.baomidou.mybatisplus.core.toolkit.PluginUtils
 *
 * @author benym
 * @since 2023/9/4 13:38
 */
public class PluginUtils {

    private PluginUtils(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获得真正的处理对象,可能多层代理.
     */
    @SuppressWarnings("all")
    public static <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }
}
