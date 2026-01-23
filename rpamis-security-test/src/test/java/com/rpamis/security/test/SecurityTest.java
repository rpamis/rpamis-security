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

	@Test
	@Transactional()
	@Rollback
	public void testInsertMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/insert"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus新增并校验数据-批量")
	public void testInsertListMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/insert/list"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus更新并校验数据")
	public void testUpdateMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/update"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus更新并校验数据-Wrapper方式")
	public void testUpdateMbpWrapper() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/update/wrapper"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus删除并校验数据")
	public void testDeleteMbp() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/delete"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis新增并校验数据-单条")
	public void testInsertMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/insert"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis新增并校验数据-批量")
	public void testInsertListMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/insert/list"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis新增并校验数据")
	public void testUpdateMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/update"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis删除并校验数据")
	public void testDeleteMb() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/delete"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-单条")
	public void testBase() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/baseType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-列表")
	public void testList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/listType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-Map")
	public void testMap() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mapType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-自定义返回体")
	public void testOther() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/otherType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-自定义返回体无泛型")
	public void testNoGeneric() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/otherType/noGeneric"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-嵌套VO")
	public void testNestVO() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/baseType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-嵌套List")
	public void testNestList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/listType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("脱敏-嵌套Map")
	public void testNestMap() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/mapType"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus单条查询")
	public void mbpSelectOne() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/select/one"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis-plus列表查询")
	public void mbpSelectList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/selectList"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis单条查询")
	public void mbSelectOne() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/select/one"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis列表查询")
	public void mbSelectList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/selectList"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-mybatis Map查询")
	public void mbSelectMap() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/select/map"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-深拷贝新增入库后不改变源对象引用")
	public void insertDeepTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/insert/deep"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-新增后如果修改同一个对象引用，再进行更新，能够正常加密")
	public void insertRepeat() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/insert/repeat"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-存量未加密数据解密，原值返回")
	public void decryptReturn() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/decrypt/return"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-避免重复加密")
	public void avoidRepeatEncrypt() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/avoid/repeat/encrypt"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional()
	@Rollback
	@DisplayName("加解密-兼容旧版本加解密")
	public void compatibilityOld() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/compatibility/old"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@DisplayName("加解密-嵌套解密")
	public void testNestedMapper() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/test/nested/decrypt"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@Transactional
	@Rollback
	@DisplayName("加解密-缓存隔离测试")
	public void testCacheIsolation() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/cache/isolate"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@DisplayName("加解密-查询返回null不抛异常")
	public void selectNull() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/test/select/null"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

}
