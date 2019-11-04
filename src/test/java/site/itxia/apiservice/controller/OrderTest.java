package site.itxia.apiservice.controller;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
public class OrderTest {

    @Autowired
    private MockMvc mockMvc;

    private int memberID;

    /**
     * 新建一个账号用于测试.
     */
    @Before
    public void before() throws Exception {
        System.out.println("before");
        var accountJson = (new JSONObject())
                .appendField("realName", "天皇孙笑")
                .appendField("loginName", "testaccount")
                .appendField("password", "qwer1234")
                .appendField("role", 1)
                .appendField("status", 1);
        var result = mockMvc.perform(post("/member")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(accountJson.toJSONString())
        )
                .andExpect(status().isOk())
                .andReturn();
        memberID = JsonPath.parse(result.getResponse().getContentAsString()).read("$.payload.id");
        System.out.println(memberID);
    }

    @Test
    public void testRequestOrder() throws Exception {
        var json = (new JSONObject())
                .appendField("name", "哈皮")
                .appendField("phone", "+86-8000")
                .appendField("qq", "2501314")
                .appendField("model", "🍎 macbook air")
                .appendField("warranty", 1)
                .appendField("campus", 2)
                .appendField("description", "玩不了cf");
        var result = mockMvc.perform(post("/order")
                .content(json.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.customerName", is("哈皮")))
                .andReturn();
        var id = (int) JsonPath.parse(result.getResponse().getContentAsString()).read("$.payload.id");
        System.out.println(id);
        System.out.println(result.getResponse().getContentAsString());
        //return id;
    }

    /**
     * 测试接单
     */
    @Test
    public void testHandleOrder() throws Exception {
        //添加预约单
        var orderJson = (new JSONObject())
                .appendField("name", "哈皮")
                .appendField("phone", "+86-17765603721")
                .appendField("qq", "32767")
                .appendField("model", "❀为")
                .appendField("warranty", 1)
                .appendField("campus", 2)
                .appendField("description", "玩不了csgo");
        var orderResult = mockMvc.perform(post("/order")
                .content(orderJson.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.id", notNullValue()))
                .andExpect(jsonPath("$.payload.status", is(0))) //状态码
                .andReturn();
        int orderID = JsonPath.parse(orderResult.getResponse().getContentAsString()).read("$.payload.id");

        //处理预约
        var handleJson = (new JSONObject())
                .appendField("orderID", orderID)
                .appendField("action", 0);
        var handleResult = mockMvc.perform(post("/order/" + orderID + "/handle")
                .content(handleJson.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .header("memberID", memberID)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.id", is(orderID)))
                .andExpect(jsonPath("$.payload.status", is(1)))
                .andExpect(jsonPath("$.payload.orderHistoryList", hasSize(1)))
                .andExpect(jsonPath("$.payload.orderHistoryList[0].memberID", is(memberID)))
                .andReturn();
        System.out.println(handleResult.getResponse().getContentAsString());
    }

}
