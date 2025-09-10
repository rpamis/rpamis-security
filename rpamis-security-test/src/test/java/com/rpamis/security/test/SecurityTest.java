package com.rpamis.security.test;

import com.rpamis.security.test.controller.TestController;
import org.junit.jupiter.api.BeforeEach;
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
 * @date 2023/9/13 16:51
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private TestController testController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    @Transactional()
    @Rollback
    public void testinsertMbp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/insert"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testinsertListMbp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/insert/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testupdateMbp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testupdateMbpWrapper() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/update/wrapper"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testdeleteMbp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/delete"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testinsertMb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/insert"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testinsertListMb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/insert/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testupdateMb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testdeleteMb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/delete"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testtestBase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/baseType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testtestList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/listType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testtestMap() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mapType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testOther() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/otherType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testNoGeneric() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/otherType/noGeneric"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testNestVO() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/baseType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testNestList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/listType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void testNestMap() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/nest/mapType"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void mbpSelectOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/select/one"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void mbpSelectList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mbp/selectList"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void mbSelectOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/select/one"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void mbSelectList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/selectList"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void mbSelectMap() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/mb/select/map"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void insertDeepTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/insert/deep"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void insertRepeat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/insert/repeat"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void decryptReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/decrypt/return"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void avoidRepeatEncrypt() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/avoid/repeat/encrypt"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Transactional()
    @Rollback
    public void compatibilityOld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test/compatibility/old"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
