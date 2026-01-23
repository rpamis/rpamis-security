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
package com.rpamis.security.core.mybatisplus;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;

/**
 * mybatis-plus插件工具类 拷贝自
 *
 * @see com.baomidou.mybatisplus.core.toolkit.PluginUtils
 * @author benym
 * @since 2023/9/4 13:38
 */
public class PluginUtils {

	private PluginUtils() {
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
