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
package com.rpamis.security.test;

import com.rpamis.security.starter.autoconfigure.SecurityAutoConfiguration;
import com.rpamis.security.starter.properties.SecurityProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

/**
 * properties测试
 *
 * @author benym
 * @since 2026/1/14 19:08
 */
public class PropertiesTest {

	@Configuration
	@EnableConfigurationProperties(SecurityProperties.class)
	static class TestConfig {

	}

	@Test
	@DisplayName("SM4密钥为空测试")
	public void testWithEmptyKey() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner().withUserConfiguration(TestConfig.class)
			.withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class));
		Throwable thrown = Assertions.catchThrowable(() -> contextRunner
			.withPropertyValues("rpamis.security.enable=true", "rpamis.security.algorithm.active=sm4",
					"rpamis.security.algorithm.sm4.key=", "rpamis.security.desensitization-enable=false")
			.run(context -> {
				// 如果启动成功，尝试获取bean
				if (context.containsBean("sm4SecurityAlgorithm")) {
					throw new IllegalStateException("Bean should not be created with empty key");
				}
			}));
		Assertions.assertThat(thrown)
			.isInstanceOf(IllegalStateException.class)
			.getCause()
			.isInstanceOf(BeanCreationException.class)
			.getCause()
			.isInstanceOf(BeanInstantiationException.class)
			.getCause()
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("sm4 encryption key is null, please set");
	}

	@Test
	@DisplayName("SM4密钥长度不足16位测试")
	public void testWithNotEnoughKey() {
		ApplicationContextRunner contextRunner = new ApplicationContextRunner().withUserConfiguration(TestConfig.class)
			.withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class));
		Throwable thrown = Assertions.catchThrowable(() -> contextRunner
			.withPropertyValues("rpamis.security.enable=true", "rpamis.security.algorithm.active=sm4",
					"rpamis.security.algorithm.sm4.key=123", "rpamis.security.desensitization-enable=false")
			.run(context -> {
				// 如果启动成功，尝试获取bean
				if (context.containsBean("sm4SecurityAlgorithm")) {
					throw new IllegalStateException("Bean should not be created with empty key");
				}
			}));
		Assertions.assertThat(thrown)
			.isInstanceOf(IllegalStateException.class)
			.getCause()
			.isInstanceOf(BeanCreationException.class)
			.getCause()
			.isInstanceOf(BeanInstantiationException.class)
			.getCause()
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("sm4 encryption key length is not 16 bytes, please check");
	}

}