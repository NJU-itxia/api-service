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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import site.itxia.apiservice.enumable.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author zhenxi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderHistoryTest {

    @Autowired
    private MockMvc mockMvc;

    private int normalMember1ID, normalMember2ID, adminMemberID;
    private int orderID;

    /**
     * 新建两个普通账号、一个管理员账号、一个预约单.
     */
    @Before
    public void before() throws Exception {
        normalMember1ID = addMember("member1", MemberRole.MEMBER);
        normalMember2ID = addMember("member2", MemberRole.MEMBER);
        adminMemberID = addMember("member3", MemberRole.ADMIN);

        //添加预约
        var json = (new JSONObject())
                .appendField("name", "哈皮")
                .appendField("phone", "+86-8000")
                .appendField("qq", "2501314")
                .appendField("model", "🍎 macbook air")
                .appendField("warranty", OrderWarranty.UNDER_WARRANTY.getWarranty())
                .appendField("campus", Campus.GU_LOU.getLocation())
                .appendField("description", "玩不了cf");
        var orderResult = mockMvc.perform(post("/order")
                .content(json.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andReturn();
        orderID = JsonPath.parse(orderResult.getResponse().getContentAsString()).read("$.payload.id");
    }

    /**
     * 添加成员.
     */
    private int addMember(String loginName, MemberRole memberRole) throws Exception {
        var member = (new JSONObject())
                .appendField("realName", "🐂🍺")
                .appendField("loginName", loginName)
                .appendField("password", "qwer1234")
                .appendField("role", memberRole.getRole())
                .appendField("status", MemberStatus.ENABLE.getStatus());
        var memberResult = mockMvc.perform(post("/member")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(member.toJSONString())
        )
                .andExpect(status().isOk())
                .andReturn();
        return JsonPath.parse(memberResult.getResponse().getContentAsString()).read("$.payload.id");
    }

    private ResultActions handleOrder(OrderAction action, int memberID) throws Exception {
        var json = (new JSONObject())
                .appendField("orderID", orderID)
                .appendField("action", action.getAction());
        return mockMvc.perform(post("/order/" + orderID + "/handle")
                .content(json.toJSONString())
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .header("memberID", memberID)
        );
    }

    private ResultActions retrieveOrder() throws Exception {
        return mockMvc.perform(get("/order?id=" + orderID));
    }

    /**
     * 获取预约单.
     */
    @Test
    public void testRetrieveOrder() throws Exception {
        retrieveOrder()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.id", is(orderID)))
                .andExpect(jsonPath("$.payload.status", is(OrderStatus.CREATED.getStatus())))
                .andExpect(jsonPath("$.payload.orderHistoryList", hasSize(0)));
    }

    /**
     * 测试接单.
     */
    @Test
    public void testHandleOrder() throws Exception {
        var result = handleOrder(OrderAction.ACCEPT, normalMember1ID)
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.SUCCESS.getErrCode())))
                .andExpect(jsonPath("$.payload.status", is(OrderStatus.ACCEPTED.getStatus())))
                .andReturn();
        ;
        assertEquals(result.getResponse().getContentAsString(), retrieveOrder().andReturn().getResponse().getContentAsString());
    }

    /**
     * 测试重复接单.
     */
    @Test
    public void testMutipleAcceptOrder() throws Exception {
        handleOrder(OrderAction.ACCEPT, normalMember1ID)
                .andExpect(jsonPath("$.errorCode", is(ErrorCode.SUCCESS.getErrCode())));

        handleOrder(OrderAction.ACCEPT, normalMember2ID)
                .andExpect(jsonPath("$.errorCode", not(ErrorCode.ORDER_ALREADY_ACCEPTED.getErrCode())));
    }

}
