package site.itxia.apiservice.service;

import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author zhenxi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberServiceTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 获取成员列表
     */
    @Test
    void getAllMember() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("")))
        ;
    }

    /**
     * 添加成员
     */
    @Test
    void addNewMember() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.appendField("realName", "test-add");
        jsonObject.appendField("loginName", "testaccount");
        jsonObject.appendField("password", "qwer1234");
        jsonObject.appendField("role", 1);
        jsonObject.appendField("status", 1);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/member")
                .content(jsonObject.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errCode", Matchers.is(0)))
                .andExpect(jsonPath("$.payload.id", Matchers.greaterThan(0)))
        ;
    }


}