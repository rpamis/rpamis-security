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

import com.rpamis.security.test.controller.TestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * 安全组件单元测试
 *
 * @author benym
 * @since 2023/9/13 16:51
 */
@SpringBootTest(classes = SecurityDemoWebApplication.class)
@AutoConfigureMockMvc
public class SecurityTest {

	@Autowired
	private TestController testController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
	}

	/**
	 * 测试MyBatis-Plus新增数据 验证通过Controller调用MyBatis-Plus新增数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus新增并校验数据")
	public void testInsertMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/insert"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis-Plus批量新增数据 验证通过Controller调用MyBatis-Plus批量新增数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus新增并校验数据-批量")
	public void testInsertListMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/insert/list"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis-Plus更新数据 验证通过Controller调用MyBatis-Plus更新数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus更新并校验数据")
	public void testUpdateMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/update"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis-Plus使用Wrapper方式更新数据 验证通过Controller调用MyBatis-Plus使用Wrapper方式更新数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus更新并校验数据-Wrapper方式")
	public void testUpdateMbpWrapper() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/update/wrapper"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis-Plus删除数据 验证通过Controller调用MyBatis-Plus删除数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus删除并校验数据")
	public void testDeleteMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/delete"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis新增数据 验证通过Controller调用MyBatis新增数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis新增并校验数据-单条")
	public void testInsertMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/insert"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis批量新增数据 验证通过Controller调用MyBatis批量新增数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis新增并校验数据-批量")
	public void testInsertListMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/insert/list"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis更新数据 验证通过Controller调用MyBatis更新数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis新增并校验数据")
	public void testUpdateMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/update"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis删除数据 验证通过Controller调用MyBatis删除数据时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis删除并校验数据")
	public void testDeleteMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/delete"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试单条数据脱敏 验证通过Controller调用时的单条数据脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-单条")
	public void testBase() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/baseType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试列表数据脱敏 验证通过Controller调用时的列表数据脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-列表")
	public void testList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/listType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试Map数据脱敏 验证通过Controller调用时的Map数据脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-Map")
	public void testMap() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mapType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试自定义返回体脱敏 验证通过Controller调用时的自定义返回体脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-自定义返回体")
	public void testOther() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/otherType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试自定义返回体无泛型脱敏 验证通过Controller调用时的自定义返回体无泛型脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-自定义返回体无泛型")
	public void testNoGeneric() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/otherType/noGeneric"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试嵌套VO脱敏 验证通过Controller调用时的嵌套VO脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-嵌套VO")
	public void testNestVO() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/baseType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试嵌套List脱敏 验证通过Controller调用时的嵌套List脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-嵌套List")
	public void testNestList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/listType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试嵌套Map脱敏 验证通过Controller调用时的嵌套Map脱敏功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-嵌套Map")
	public void testNestMap() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/mapType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis-Plus单条查询 验证通过Controller调用MyBatis-Plus单条查询时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus单条查询")
	public void mbpSelectOne() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/select/one"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis-Plus列表查询 验证通过Controller调用MyBatis-Plus列表查询时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus列表查询")
	public void mbpSelectList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/selectList"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis单条查询 验证通过Controller调用MyBatis单条查询时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis单条查询")
	public void mbSelectOne() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/select/one"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis列表查询 验证通过Controller调用MyBatis列表查询时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis列表查询")
	public void mbSelectList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/selectList"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试MyBatis Map查询 验证通过Controller调用MyBatis Map查询时的加解密功能
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis Map查询")
	public void mbSelectMap() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/select/map"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试深拷贝新增入库后不改变源对象引用 验证新增数据时的深拷贝功能，确保源对象引用不被改变
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-深拷贝新增入库后不改变源对象引用")
	public void insertDeepTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/insert/deep"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试新增后修改同一对象引用再更新 验证新增后如果修改同一个对象引用，再进行更新时能够正常加密
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-新增后如果修改同一个对象引用，再进行更新，能够正常加密")
	public void insertRepeat() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/insert/repeat"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试存量未加密数据解密 验证存量未加密数据解密时能够原值返回
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-存量未加密数据解密，原值返回")
	public void decryptReturn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/decrypt/return"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试避免重复加密 验证系统能够避免对已加密数据进行重复加密
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-避免重复加密")
	public void avoidRepeatEncrypt() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/avoid/repeat/encrypt"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试兼容旧版本加解密 验证系统能够兼容旧版本的加解密方式
	 */
	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-兼容旧版本加解密")
	public void compatibilityOld() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/compatibility/old"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试嵌套解密 验证系统能够正确处理嵌套解密场景
	 */
	@Test
	@DisplayName("加解密-嵌套解密")
	public void testNestedMapper() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test/nested/decrypt"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试缓存隔离 验证系统能够正确处理缓存隔离场景
	 */
	@Test
	@Transactional
	@Rollback
	@DisplayName("加解密-缓存隔离测试")
	public void testCacheIsolation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/cache/isolate"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	/**
	 * 测试查询返回null不抛异常 验证系统在查询返回null时不会抛出异常
	 */
	@Test
	@DisplayName("加解密-查询返回null不抛异常")
	public void selectNull() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/select/null"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

}
