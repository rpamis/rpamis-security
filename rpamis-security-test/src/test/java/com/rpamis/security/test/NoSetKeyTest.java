package com.rpamis.security.test;

import com.rpamis.security.starter.autoconfigure.SecurityAutoConfiguration;
import com.rpamis.security.starter.properties.SecurityProperties;
import org.junit.Test;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.*;

/**
 * sm4算法未设置key测试
 *
 * @author benym
 * @date 2026/1/14 19:08
 */
public class NoSetKeyTest {

    @Configuration
    @EnableConfigurationProperties(SecurityProperties.class)
    static class TestConfig {
    }

    @Test
    public void testWithEmptyKey() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(TestConfig.class)
                .withConfiguration(AutoConfigurations.of(SecurityAutoConfiguration.class));
        Throwable thrown = catchThrowable(() -> contextRunner
                .withPropertyValues(
                        "rpamis.security.enable=true",
                        "rpamis.security.algorithm.active=sm4",
                        "rpamis.security.algorithm.sm4.key=",
                        "rpamis.security.desensitization-enable=false"
                )
                .run(context -> {
                    // 如果启动成功，尝试获取bean
                    if (context.containsBean("sm4SecurityAlgorithm")) {
                        throw new IllegalStateException("Bean should not be created with empty key");
                    }
                })
        );
        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .getCause()
                .isInstanceOf(BeanCreationException.class)
                .getCause()
                .isInstanceOf(BeanInstantiationException.class)
                .getCause()
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("sm4 encryption key is null, please set");
    }
}