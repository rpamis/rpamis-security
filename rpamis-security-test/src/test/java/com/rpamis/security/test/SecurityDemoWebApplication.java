package com.rpamis.security.test;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("com.rpamis.security.test.dao")
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SpringBootApplication()
public class SecurityDemoWebApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityDemoWebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoWebApplication.class, args);
	}

}
