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
import site.itxia.apiservice.enumable.ErrorCode;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.enumable.MemberStatus;

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
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCode.SUCCESS.getErrCode())))
                .andExpect(jsonPath("$.payload.id", Matchers.greaterThan(0)))
        ;
    }

    @Test
    void addDuplicateMember() throws Exception {
        final var duplicateLoginName = "duname";
        JSONObject member1 = (new JSONObject())
                .appendField("realName", "name")
                .appendField("loginName", duplicateLoginName)
                .appendField("password", "qwer1234")
                .appendField("role", MemberRole.MEMBER.getRole())
                .appendField("status", MemberStatus.ENABLE.getStatus());
        mockMvc.perform(post("/member")
                .content(member1.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCode.SUCCESS.getErrCode())));
        ;
        //添加登录名相同的用户
        JSONObject member2 = (new JSONObject())
                .appendField("realName", "name2")
                .appendField("loginName", duplicateLoginName)
                .appendField("password", "qwer12345")
                .appendField("role", MemberRole.ADMIN.getRole())
                .appendField("status", MemberStatus.ENABLE.getStatus());
        mockMvc.perform(post("/member")
                .content(member2.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        )
                .andExpect(jsonPath("$.errorCode", Matchers.is(ErrorCode.MEMBER_ALREADY_EXISTS.getErrCode())));

    }

}