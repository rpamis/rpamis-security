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

import com.rpamis.security.test.dao.TestVersionMapper;
import com.rpamis.security.test.dao.TestVersionV2Mapper;
import com.rpamis.security.test.domain.TestVersionDO;
import com.rpamis.security.test.domain.TestVersionV2DO;
import com.rpamis.security.test.mybatis.CustomMybatisInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 自定义 MyBatis 拦截器启用场景集成测试。
 *
 * @author benym
 */
@SpringBootTest(classes = SecurityDemoWebApplication.class)
@ActiveProfiles("h2")
@Import(CustomMybatisInterceptorEncryptionTest.CustomMybatisInterceptorTestConfiguration.class)
@Transactional
@Rollback
class CustomMybatisInterceptorEncryptionTest {

	@Autowired
	private TestVersionMapper testVersionMapper;

	@Autowired
	private TestVersionV2Mapper testVersionV2Mapper;

	@BeforeEach
	void setUp() {
		CustomMybatisInterceptor.reset();
	}

	@Test
	@DisplayName("加解密-启用CustomMybatisInterceptor时仍正常加密")
	void shouldEncryptNormallyWhenCustomMybatisInterceptorEnabled() {
		TestVersionDO testVersionDO = new TestVersionDO();
		testVersionDO.setName("张三");
		testVersionDO.setIdCard("500101111118181952");
		testVersionDO.setPhone("12345678965");
		testVersionMapper.insertSelective(testVersionDO);
		TestVersionV2DO selectResult = testVersionV2Mapper.selectById(testVersionDO.getId());
		String nameInDb = selectResult.getName();
		String idCard = selectResult.getIdCard();
		String phone = selectResult.getPhone();
		Assert.isTrue("ENC_SM4_4e123e9554b4a5b30b53b5b27c4deb59".equals(nameInDb), "数据库姓名校验失败");
		Assert.isTrue("ENC_SM4_afea507cb1a88c25807d29b905b49bec5222893c3ff712bf9841e10d0ec1ac12".equals(idCard),
				"数据库姓名校验失败");
		Assert.isTrue("ENC_SM4_93250b1e19518cc1b36af85c54066051".equals(phone), "数据库姓名校验失败");
		assertTrue(CustomMybatisInterceptor.getInvocationCount() > 0, "启用自定义 MyBatis 拦截器后应至少拦截一次参数设置");
	}

	@TestConfiguration
	static class CustomMybatisInterceptorTestConfiguration {

		@Bean
		CustomMybatisInterceptor customMybatisInterceptor() {
			return new CustomMybatisInterceptor();
		}

	}

}
