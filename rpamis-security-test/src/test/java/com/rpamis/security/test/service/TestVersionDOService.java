package com.rpamis.security.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rpamis.security.test.domain.TestVersionDO;

import java.util.List;

/**
 * @author benym
 * @description 针对表【test_version】的数据库操作Service
 * @createDate 2023-09-07 17:14:56
 */
public interface TestVersionDOService extends IService<TestVersionDO> {

	/**
	 * 测试脱敏基础数据
	 * @return TestVersionDO
	 */
	TestVersionDO testDesensite();

	/**
	 * 测试脱敏list数据
	 * @return List<TestVersionDO>
	 */
	List<TestVersionDO> testDesensiteList();

}
