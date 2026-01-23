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
package com.rpamis.security.core.mybatis;

import com.rpamis.security.core.utils.SecurityResolver;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Mybatis解密拦截器，出库时解密
 *
 * @author benym
 * @since 2023/8/31 16:22
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class MybatisDecryptInterceptor implements Interceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MybatisDecryptInterceptor.class);

	private final SecurityResolver securityResolver;

	public MybatisDecryptInterceptor(SecurityResolver securityResolver) {
		this.securityResolver = securityResolver;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		try {
			Object result = invocation.proceed();
			if (Objects.isNull(result)) {
				return null;
			}
			return securityResolver.decryptFiled(result);
		}
		catch (IllegalAccessException | InvocationTargetException e) {
			// 如果是invocation.proceed()过程执行真实方法异常了，则直接抛出，非security插件问题
			throw e;
		}
		catch (Exception e) {
			LOGGER.error(
					"decrypt process: rpamis security mybatis decrypt interceptor unknown exception, the original value is returned, and no impact is caused",
					e);
			return invocation.proceed();
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

}
