package com.rpamis.security.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpamis.security.test.dao.TestVersionMapper;
import com.rpamis.security.test.domain.TestVersionDO;
import com.rpamis.security.test.service.TestVersionDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author benym
 * @description 针对表【test_version】的数据库操作Service实现
 * @createDate 2023-09-07 17:14:56
 */
@Service
public class TestVersionDOServiceImpl extends ServiceImpl<TestVersionMapper, TestVersionDO>
        implements TestVersionDOService {

    @Autowired
    private TestVersionMapper testVersionMapper;

    @Override
    public TestVersionDO testDesensite() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("18523578454");
        testVersionMapper.insert(testVersionDO);
        return testVersionMapper.selectById(testVersionDO.getId());
    }

    @Override
    public List<TestVersionDO> testDesensiteList() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("18523578454");
        TestVersionDO testVersionDO2 = new TestVersionDO();
        testVersionDO2.setName("李四");
        testVersionDO2.setIdCard("500101111118181953");
        testVersionDO2.setPhone("18523578456");
        List<TestVersionDO> testVersionDOList = new ArrayList<>();
        testVersionDOList.add(testVersionDO);
        testVersionDOList.add(testVersionDO2);
        testVersionMapper.batchInsertWithList(testVersionDOList);
        List<Long> testIdList = new ArrayList<>();
        testIdList.add(testVersionDO.getId());
        testIdList.add(testVersionDO2.getId());
        return testVersionMapper.selectAllByIdList(testIdList);
    }
}




