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
     *
     * @param id id
     * @return TestNestDecryptVO
     */
    TestNestDecryptVO queryUserInfoById(@Param("id") int id);
}




