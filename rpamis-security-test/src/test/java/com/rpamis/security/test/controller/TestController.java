package com.rpamis.security.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rpamis.common.dto.response.Response;
import com.rpamis.common.utils.RpamisBeanUtil;
import com.rpamis.security.annotation.Desensitizationed;
import com.rpamis.security.core.algorithm.SecurityAlgorithm;
import com.rpamis.security.core.utils.SecurityUtils;
import com.rpamis.security.test.dao.TestNestMapper;
import com.rpamis.security.test.dao.TestVersionMapper;
import com.rpamis.security.test.dao.TestVersionV2Mapper;
import com.rpamis.security.test.domain.*;
import com.rpamis.security.test.service.TestVersionDOService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author benym
 * @date 2023/9/7 17:10
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestVersionMapper testVersionMapper;

    @Autowired
    private TestVersionV2Mapper testVersionV2Mapper;

    @Autowired
    private TestNestMapper testNestMapper;

    @Autowired
    private TestVersionDOService testVersionDOService;

    @Resource
    private SecurityAlgorithm securityAlgorithm;

    @Resource
    private SecurityUtils securityUtils;

    /**
     * mybatis-plus 新增并校验数据
     */
    @PostMapping("/mbp/insert")
    public void insertMbp() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectById(testVersionDO.getId());
        Assert.isTrue("张三".equals(selectResult.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
    }

    /**
     * mybatis-plus 新增并校验数据
     */
    @PostMapping("/mbp/insert/list")
    public void insertListMbp() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        TestVersionDO testVersionDO2 = new TestVersionDO();
        testVersionDO2.setName("李四");
        testVersionDO2.setIdCard("500101111118181953");
        testVersionDO2.setPhone("18523578456");
        List<TestVersionDO> testVersionDOList = new ArrayList<>();
        testVersionDOList.add(testVersionDO);
        testVersionDOList.add(testVersionDO2);
        testVersionDOService.saveBatch(testVersionDOList);
        List<Long> testIdList = new ArrayList<>();
        testIdList.add(testVersionDO.getId());
        testIdList.add(testVersionDO2.getId());
        List<TestVersionDO> selectResult = testVersionMapper.selectAllByIdList(testIdList);
        Assert.isTrue("王五".equals(selectResult.get(0).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.get(0).getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.get(0).getPhone()), "电话校验失败");
        Assert.isTrue("李四".equals(selectResult.get(1).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181953".equals(selectResult.get(1).getIdCard()), "身份证校验失败");
        Assert.isTrue("18523578456".equals(selectResult.get(1).getPhone()), "电话校验失败");
    }

    /**
     * mybatis-plus 更新并校验数据
     */
    @PostMapping("/mbp/update")
    public void updateMbp() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectById(testVersionDO.getId());
        String nameInDb = selectResult.getName();
        Assert.isTrue("张三".equals(nameInDb), "数据库姓名校验失败");
        TestVersionDO test2 = RpamisBeanUtil.copy(testVersionDO, TestVersionDO.class);
        test2.setName("李四");
        testVersionMapper.updateById(test2);
        TestVersionDO result = testVersionMapper.selectById(testVersionDO.getId());
        Assert.isTrue("李四".equals(result.getName()), "更新姓名校验失败");
    }

    /**
     * mybatis-plus 更新并校验数据 wrapper
     */
    @PostMapping("/mbp/update/wrapper")
    public void updateMbpWrapper() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectById(testVersionDO.getId());
        String nameInDb = selectResult.getName();
        Assert.isTrue("张三".equals(nameInDb), "数据库姓名校验失败");
        UpdateWrapper<TestVersionDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(TestVersionDO::getName, securityAlgorithm.encrypt("李四"))
                .eq(TestVersionDO::getId, selectResult.getId());
        testVersionMapper.update(null, updateWrapper);
        TestVersionDO result = testVersionMapper.selectById(selectResult.getId());
        Assert.isTrue("李四".equals(result.getName()), "更新姓名校验失败");
    }

    /**
     * mybatis-plus 删除并校验数据
     */
    @PostMapping("/mbp/delete")
    public void deleteMbp() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        int row = testVersionMapper.deleteById(testVersionDO.getId());
        Assert.isTrue(row != 0, "删除数据失败");
    }

    /**
     * mybatis 新增并校验数据
     */
    @PostMapping("/mb/insert")
    public void insertMb() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insertSelective(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectByTestId(testVersionDO.getId());
        Assert.isTrue("王五".equals(selectResult.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
    }

    /**
     * mybatis 新增并校验数据 list
     */
    @PostMapping("/mb/insert/list")
    public void insertListMb() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
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
        List<TestVersionDO> selectResult = testVersionMapper.selectAllByIdList(testIdList);
        Assert.isTrue("王五".equals(selectResult.get(0).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.get(0).getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.get(0).getPhone()), "电话校验失败");
        Assert.isTrue("李四".equals(selectResult.get(1).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181953".equals(selectResult.get(1).getIdCard()), "身份证校验失败");
        Assert.isTrue("18523578456".equals(selectResult.get(1).getPhone()), "电话校验失败");
    }

    /**
     * mybatis 更新并校验数据
     */
    @PostMapping("/mb/update")
    public void updateMb() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insertSelective(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectByTestId(testVersionDO.getId());
        String nameInDb = selectResult.getName();
        Assert.isTrue("王五".equals(nameInDb), "数据库姓名校验失败");
        TestVersionDO test2 = RpamisBeanUtil.copy(testVersionDO, TestVersionDO.class);
        test2.setName("李四");
        testVersionMapper.updateSelective(test2);
        TestVersionDO result = testVersionMapper.selectByTestId(selectResult.getId());
        Assert.isTrue("李四".equals(result.getName()), "更新姓名校验失败");
    }

    /**
     * mybatis 删除并校验数据
     */
    @PostMapping("/mb/delete")
    public void deleteMb() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("王五");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insertSelective(testVersionDO);
        int row = testVersionMapper.deleteByTestId(testVersionDO.getId());
        Assert.isTrue(row != 0, "删除数据失败");
    }

    /**
     * 获取脱敏数据-base类型
     *
     * @return TestVO
     */
    @PostMapping("/baseType")
    @Desensitizationed
    public TestVO testBase() {
        TestVersionDO result = testVersionDOService.testDesensite();
        return RpamisBeanUtil.copy(result, TestVO.class);
    }

    /**
     * 获取脱敏数据-list类型
     *
     * @return List<TestVO>
     */
    @PostMapping("/listType")
    @Desensitizationed
    public List<TestVO> testList() {
        List<TestVersionDO> testVersionDOList = testVersionDOService.testDesensiteList();
        return RpamisBeanUtil.copyToList(testVersionDOList, TestVO.class);
    }

    /**
     * 获取脱敏数据-map类型
     *
     * @return Map<String, TestVO>
     */
    @PostMapping("/mapType")
    @Desensitizationed
    public Map<String, TestVO> testMap() {
        TestVersionDO testVersionDO = testVersionDOService.testDesensite();
        TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
        test.setCustomFiled("4984189sdwq");
        Map<String, TestVO> map = new HashMap<>();
        map.put("test", test);
        return map;
    }

    /**
     * 获取脱敏数据-自定义返回体
     *
     * @return RemoteResult<TestVO>
     */
    @PostMapping("/otherType")
    @Desensitizationed
    public Response<TestVO> testOther() {
        TestVersionDO testVersionDO = testVersionDOService.testDesensite();
        TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
        return Response.success(test);
    }

    /**
     * 获取脱敏数据-自定义返回体-无泛型
     *
     * @return RemoteResult
     */
    @PostMapping("/otherType/noGeneric")
    @Desensitizationed
    public Response testNoGeneric() {
        TestVersionDO testVersionDO = testVersionDOService.testDesensite();
        TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
        return Response.success(test);
    }

    /**
     * 获取脱敏数据-嵌套脱敏-base
     *
     * @return TestNestVO
     */
    @PostMapping("/nest/baseType")
    @Desensitizationed
    public TestNestVO testNestVO() {
        TestVersionDO testVersionDO = testVersionDOService.testDesensite();
        TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
        TestNestVO testNestVO = new TestNestVO();
        testNestVO.setId(1L);
        testNestVO.setName("张三");
        testNestVO.setTestVO(test);
        return testNestVO;
    }

    /**
     * 获取脱敏数据-嵌套脱敏-list
     *
     * @return TestNestVO
     */
    @PostMapping("/nest/listType")
    @Desensitizationed
    public TestNestVO testNestList() {
        TestVersionDO testVersionDO = testVersionDOService.testDesensite();
        TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
        TestNestVO testNestVO = new TestNestVO();
        testNestVO.setId(1L);
        testNestVO.setName("张三");
        testNestVO.setTestVOList(Collections.singletonList(test));
        return testNestVO;
    }

    /**
     * 获取脱敏数据-嵌套脱敏-map
     *
     * @return TestNestVO
     */
    @PostMapping("/nest/mapType")
    @Desensitizationed
    public TestNestVO testNestMap() {
        TestVersionDO testVersionDO = testVersionDOService.testDesensite();
        TestVO test = RpamisBeanUtil.copy(testVersionDO, TestVO.class);
        Map<String, TestVO> map = new HashMap<>();
        map.put("test", test);
        TestNestVO testNestVO = new TestNestVO();
        testNestVO.setId(1L);
        testNestVO.setName("张三");
        testNestVO.setTestVOMap(map);
        return testNestVO;
    }

    /**
     * 获取解密数据-mybatis-plus-one
     */
    @PostMapping("/mbp/select/one")
    public void mbpSelectOne() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        QueryWrapper<TestVersionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(TestVersionDO::getId, testVersionDO.getId());
        TestVersionDO selectResult = testVersionMapper.selectOne(queryWrapper);
        Assert.isTrue("张三".equals(selectResult.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
    }

    /**
     * 获取解密数据-mybatis-plus-list
     */
    @PostMapping("/mbp/selectList")
    public void mbpSelectList() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO testVersion2 = new TestVersionDO();
        testVersion2.setName("李四");
        testVersion2.setIdCard("500101111118181952");
        testVersion2.setPhone("12345678965");
        testVersionMapper.insert(testVersion2);
        List<Long> idList = new ArrayList<>();
        idList.add(testVersionDO.getId());
        idList.add(testVersion2.getId());
        QueryWrapper<TestVersionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(TestVersionDO::getId, idList);
        List<TestVersionDO> selectResult = testVersionMapper.selectList(queryWrapper);
        Assert.isTrue("张三".equals(selectResult.get(0).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.get(0).getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.get(0).getPhone()), "电话校验失败");
        Assert.isTrue("李四".equals(selectResult.get(1).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.get(1).getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.get(1).getPhone()), "电话校验失败");
    }

    /**
     * 获取解密数据-mybatis-one
     */
    @PostMapping("/mb/select/one")
    public void mbSelectOne() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectByTestId(testVersionDO.getId());
        Assert.isTrue("张三".equals(selectResult.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
    }

    /**
     * 获取解密数据-mybatis-list
     */
    @PostMapping("/mb/selectList")
    public void mbSelectList() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO testVersion2 = new TestVersionDO();
        testVersion2.setName("李四");
        testVersion2.setIdCard("500101111118181952");
        testVersion2.setPhone("12345678965");
        testVersionMapper.insert(testVersion2);
        List<Long> idList = new ArrayList<>();
        idList.add(testVersionDO.getId());
        idList.add(testVersion2.getId());
        List<TestVersionDO> selectResult = testVersionMapper.selectAllByIdList(idList);
        Assert.isTrue("张三".equals(selectResult.get(0).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.get(0).getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.get(0).getPhone()), "电话校验失败");
        Assert.isTrue("李四".equals(selectResult.get(1).getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.get(1).getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.get(1).getPhone()), "电话校验失败");
    }

    /**
     * 获取解密数据-mybatis-map
     */
    @PostMapping("/mb/select/map")
    public void mbSelectMap() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        Map<String, TestVersionDO> selectResultMap = testVersionMapper.selectMapByTestId(testVersionDO.getId());
        TestVersionDO selectResult = selectResultMap.get(testVersionDO.getId());
        Assert.isTrue("张三".equals(selectResult.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
    }

    /**
     * 新增入库后不改变源对象引用，深拷贝单测
     */
    @PostMapping("/insert/deep")
    public void insertDeepTest() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        Assert.isTrue("张三".equals(testVersionDO.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(testVersionDO.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(testVersionDO.getPhone()), "电话校验失败");
    }

    /**
     * 新增后，如果修改同一个对象引用，再进行更新，能够正常加密
     */
    @PostMapping("/insert/repeat")
    public void insertRepeat() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionDO selectResult = testVersionMapper.selectById(testVersionDO.getId());
        String nameInDb = selectResult.getName();
        Assert.isTrue("张三".equals(nameInDb), "数据库姓名校验失败");
        // 同一上文对象
        testVersionDO.setName("李四");
        // 更新后数据库为李四的加密字段
        testVersionMapper.updateById(testVersionDO);
        TestVersionDO result = testVersionMapper.selectById(testVersionDO.getId());
        Assert.isTrue("李四".equals(result.getName()), "更新姓名校验失败");
    }

    /**
     * 存量未加密数据解密，原值返回
     */
    @PostMapping("/decrypt/return")
    public void decryptReturn() {
        TestVersionDO selectResult = testVersionMapper.selectById(1);
        Assert.isTrue("张三".equals(selectResult.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(selectResult.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(selectResult.getPhone()), "电话校验失败");
    }

    /**
     * 防止已经加密的字段重复加密
     * 如数据库已加密，但出库时加密字段实体未采用注解标识，则此时返回的是加密数据
     * 此加密数据又被赋值给另一个采用注解标识的实体字段，期望不会重复加密
     */
    @PostMapping("/avoid/repeat/encrypt")
    public void avoidRepeatEncrypt() {
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        TestVersionV2DO selectResult = testVersionV2Mapper.selectById(testVersionDO.getId());
        String nameInDb = selectResult.getName();
        String idCard = selectResult.getIdCard();
        String phone = selectResult.getPhone();
        Assert.isTrue("ENC_SM4_4e123e9554b4a5b30b53b5b27c4deb59".equals(nameInDb), "数据库姓名校验失败");
        Assert.isTrue("ENC_SM4_afea507cb1a88c25807d29b905b49bec5222893c3ff712bf9841e10d0ec1ac12".equals(idCard), "数据库姓名校验失败");
        Assert.isTrue("ENC_SM4_93250b1e19518cc1b36af85c54066051".equals(phone), "数据库姓名校验失败");
        testVersionDO.setName(selectResult.getName());
        testVersionMapper.updateById(testVersionDO);
        TestVersionDO repeatTestVersion = testVersionMapper.selectById(testVersionDO.getId());
        Assert.isTrue("张三".equals(repeatTestVersion.getName()), "姓名校验失败");
    }

    /**
     * 兼容rpamis-security 1.0.2以前老版本已加密数据解密，正常解密后返回
     */
    @PostMapping("/compatibility/old")
    public void compatibilityOld() {
        // 老数据加密方式，不带ENC_SM4_前缀
        TestVersionDO testVersionDO = testVersionMapper.selectById(3);
        Assert.isTrue("张三".equals(testVersionDO.getName()), "姓名校验失败");
        Assert.isTrue("500101111118181952".equals(testVersionDO.getIdCard()), "身份证校验失败");
        Assert.isTrue("12345678965".equals(testVersionDO.getPhone()), "电话校验失败");
    }

    /**
     * 嵌套解密测试
     */
    @GetMapping("/nested/decrypt")
    public void testNestedDecrypt() {
        TestNestDecryptVO testNestDecryptVO =  testNestMapper.queryUserInfoById(1);
        String name = testNestDecryptVO.getName();
        TestVersionDO testVersionDO = testNestDecryptVO.getTestVersionDO();
        TestNestDO testNestDO = testNestDecryptVO.getTestNestDO();
        Assert.isTrue("张三".equals(name), "嵌套解密-外层姓名校验失败");
        Assert.isTrue("张三".equals(testVersionDO.getName()), "嵌套解密-姓名校验失败");
        Assert.isTrue("500101111118181952".equals(testVersionDO.getIdCard()), "嵌套解密-身份证校验失败");
        Assert.isTrue("12345678965".equals(testVersionDO.getPhone()), "嵌套解密-电话校验失败");
        Assert.isTrue("12345678965".equals(testNestDO.getUserAccount()), "嵌套解密-用户账号校验失败");
    }

    /**
     * 缓存隔离测试
     */
    @PostMapping("/cache/isolate")
    public void testCacheIsolate() {
        // 先查询，会让解密缓存字段构建
        QueryWrapper<TestVersionV2DO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(TestVersionV2DO::getId, 1);
        TestVersionV2DO testVersionV2DO = testVersionV2Mapper.selectOne(queryWrapper);
        Assert.isTrue(testVersionV2DO != null, "查询数据失败");
        // 再新增，看是否会影响加密部分，期望不加密
        TestVersionDO testVersionDO = new TestVersionDO();
        testVersionDO.setName("张三");
        testVersionDO.setIdCard("500101111118181952");
        testVersionDO.setPhone("12345678965");
        testVersionMapper.insert(testVersionDO);
        QueryWrapper<TestVersionDO> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda()
                .eq(TestVersionDO::getId, testVersionDO.getId());
        TestVersionDO selected = testVersionMapper.selectOne(queryWrapper2);
        Assert.isTrue(!securityUtils.checkHasBeenEncrypted(selected.getName()), "name缓存隔离测试失败");
        Assert.isTrue(!securityUtils.checkHasBeenEncrypted(selected.getIdCard()), "idCard缓存隔离测试失败");
        Assert.isTrue(!securityUtils.checkHasBeenEncrypted(selected.getPhone()), "phone缓存隔离测试失败");
    }

    /**
     * 查询返回null测试，正常返回null内部会处理为list类型，不会引起框架内部报错
     */
    @PostMapping("/select/null")
    public void selectNull() {
        QueryWrapper<TestVersionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(TestVersionDO::getId, 999);
        // 断言执行selectOne不会抛出NullPointerException
        TestVersionDO testVersionDO = Assertions.assertDoesNotThrow(
                () -> testVersionMapper.selectOne(queryWrapper),
                "查询不存在的记录不应该抛出NullPointerException"
        );
        // 验证结果确实是null
        Assertions.assertNull(testVersionDO, "查询不存在的记录应该返回null");

    }
}
