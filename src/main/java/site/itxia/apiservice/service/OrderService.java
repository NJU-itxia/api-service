package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.Order;
import site.itxia.apiservice.data.entity.OrderHistory;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.data.repository.OrderHistoryRepository;
import site.itxia.apiservice.data.repository.OrderRepository;
import site.itxia.apiservice.dto.OrderDTO;
import site.itxia.apiservice.dto.OrderHistoryDTO;
import site.itxia.apiservice.enumable.OrderStatus;
import site.itxia.apiservice.mapper.OrderHistoryMapper;
import site.itxia.apiservice.mapper.OrderMapper;
import site.itxia.apiservice.util.DateUtil;
import site.itxia.apiservice.vo.HandleOrderVo;
import site.itxia.apiservice.vo.RequestOrderVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenxi
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    private OrderMapper orderMapper = OrderMapper.INSTANCE;
    private OrderHistoryMapper orderHistoryMapper = OrderHistoryMapper.INSTANCE;

    /**
     * 收到新的预约单.
     *
     * @param requestOrderVo 预约单表格信息.
     * @return 预约单.(包含ID)
     */
    public OrderDTO addOrder(RequestOrderVo requestOrderVo) {
        var order = orderMapper.requestOrderVoToOrder(requestOrderVo);
        //设置为当前时间
        order.setTime(DateUtil.getCurrentUnixTime());
        order.setStatus(OrderStatus.CREATED);
        var savedOrder = orderRepository.save(order);
        var orderID = savedOrder.getId();
        return getOrder(orderID);
    }

    /**
     * 获取预约单。(单个)
     *
     * @param id 预约单ID.
     * @return 预约单.
     */
    public OrderDTO getOrder(int id) {
        var order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return null;
        }
        var dto = orderMapper.orderToOrderDTO(order.get());
        dto.setOrderHistoryList(getOrderHistoryDTOByOrderID(dto.getId()));
        return dto;
    }

    public OrderDTO handleOrder(int memberID, HandleOrderVo handleOrderVo) {
        //TODO 验证状态

        var orderID = handleOrderVo.getOrderID();
        var orderOptional = orderRepository.findById(orderID);
        if (orderOptional.isEmpty()) {
            //error
        }
        var order = orderOptional.get();
        var dto = orderMapper.orderToOrderDTO(order);

        //保存历史记录
        var orderHistory = orderHistoryMapper.handleToOrderHistory(handleOrderVo);
        orderHistory.setMemberID(memberID);
        orderHistory.setTime(DateUtil.getCurrentUnixTime());
        orderHistory = orderHistoryRepository.save(orderHistory);

        //更新预约单状态
        var updatedOrder = updateOrderStatus(orderID, orderHistory.getAction().getAction());

        return getOrder(orderID);
    }

    /**
     * TODO 错误状态判断
     * TODO 使用枚举代替硬编码数字
     */
    private Order updateOrderStatus(int orderID, int action) {
        OrderStatus newState;
        var order = orderRepository.findById(orderID).get();
        //憨批java语法
        switch (action) {
            case 0:
                newState = OrderStatus.ACCEPTED;
                break;
            case 1:
                newState = OrderStatus.CREATED;
                break;
            case 2:
                newState = OrderStatus.COMPLETED;
                break;
            case 3:
                newState = OrderStatus.CANCELED;
                break;
            case 4:
                newState = OrderStatus.ABANDONED;
                break;
            default:
                newState = OrderStatus.CREATED;
        }
        order.setStatus(newState);
        return orderRepository.save(order);
    }

    /**
     * 获取预约单的全部历史记录.
     *
     * @param orderID 预约单id.
     * @return 历史记录列表.
     */
    private List<OrderHistoryDTO> getOrderHistoryDTOByOrderID(int orderID) {
        //TODO 优化性能，直接用sql筛选
        var orderHistoryDTOList = new ArrayList<OrderHistoryDTO>();
        for (OrderHistory orderHistory : orderHistoryRepository.findAll()) {
            if (orderHistory.getOrderID() == orderID) {
                var dto = orderHistoryMapper.toDTO(orderHistory);
                //插入成员名
                dto.setMemberName(memberRepository.findById(dto.getMemberID()).getRealName());
                orderHistoryDTOList.add(dto);
            }
        }
        return orderHistoryDTOList;
    }

}
