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
