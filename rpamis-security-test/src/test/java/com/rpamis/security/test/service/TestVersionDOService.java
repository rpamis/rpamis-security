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
