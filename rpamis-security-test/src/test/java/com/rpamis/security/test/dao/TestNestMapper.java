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
package com.rpamis.security.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpamis.security.test.domain.TestNestDO;
import com.rpamis.security.test.domain.TestNestDecryptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author benym
 * @description 针对表【test_nest】的数据库操作Mapper
 * @createDate 2026-01-12 19:19:45
 */
@Mapper
public interface TestNestMapper extends BaseMapper<TestNestDO> {

	/**
	 * 查询用户信息通过ID
	 * @param id id
	 * @return TestNestDecryptVO
	 */
	TestNestDecryptVO queryUserInfoById(@Param("id") int id);

}
