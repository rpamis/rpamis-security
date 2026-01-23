package com.rpamis.security.test;

import com.rpamis.security.test.dao.TestVersionMapper;
import com.rpamis.security.test.dao.TestVersionV2Mapper;
import com.rpamis.security.test.domain.TestVersionDO;
import com.rpamis.security.test.domain.TestVersionV2DO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.Assert;

/**
 * 默认安全算法测试
 *
 * @author benym
 * @since 2026/1/14 17:35
 */
@SpringBootTest
public class DefaultAlgorithmTest {

	@Autowired
	private TestVersionMapper testVersionMapper;

	@Autowired
	private TestVersionV2Mapper testVersionV2Mapper;

	/**
	 * Dynamically configure properties
	 */
	@DynamicPropertySource
	static void registerProperties(DynamicPropertyRegistry registry) {
		registry.add("rpamis.security.algorithm.active", () -> "no");
	}

	@Test
	@DisplayName("默认安全算法-插入数据返回原值测试")
	public void testSelectOriginalReturn() {
		TestVersionDO testVersionDO = new TestVersionDO();
		testVersionDO.setName("张三");
		testVersionDO.setIdCard("500101111118181952");
		testVersionDO.setPhone("12345678965");
		testVersionMapper.insert(testVersionDO);
		TestVersionV2DO selectResult = testVersionV2Mapper.selectById(testVersionDO.getId());
		Assert.isTrue("张三".equals(selectResult.getName()), "姓名校验失败");
		Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
		Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
	}

}
