package com.rpamis.security.test.mybatis;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CustomMybatisInterceptor
 *
 * @author benym
 */
@Intercepts({ @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class) })
public class CustomMybatisInterceptor implements Interceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomMybatisInterceptor.class);

	private static final AtomicInteger INVOCATION_COUNT = new AtomicInteger();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		INVOCATION_COUNT.incrementAndGet();
		LOGGER.info("CustomMybatisInterceptor intercept method called");
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public static void reset() {
		INVOCATION_COUNT.set(0);
	}

	public static int getInvocationCount() {
		return INVOCATION_COUNT.get();
	}

}
