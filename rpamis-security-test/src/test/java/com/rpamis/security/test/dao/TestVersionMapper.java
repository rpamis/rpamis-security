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
import com.rpamis.security.test.domain.TestVersionDO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author benym
 * @description 针对表【test_version】的数据库操作Mapper
 * @createDate 2023-07-05 15:18:00
 */
@Mapper
public interface TestVersionMapper extends BaseMapper<TestVersionDO> {

	int insertSelective(TestVersionDO testVersionDO);

	int batchInsertWithList(@Param("testVersionDOList") List<TestVersionDO> testVersionDOList);

	TestVersionDO selectByTestId(@Param("id") Long id);

	int deleteByTestId(@Param("id") Long id);

	int updateSelective(TestVersionDO testVersionDO);

	List<TestVersionDO> selectAllByIdList(@Param("idList") List<Long> id);

	@MapKey("id")
	Map<String, TestVersionDO> selectMapByTestId(@Param("id") Long id);

}
