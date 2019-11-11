package site.itxia.apiservice.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.Order;
import site.itxia.apiservice.data.entity.OrderHistory;
import site.itxia.apiservice.data.entity.OrderUpload;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.data.repository.OrderHistoryRepository;
import site.itxia.apiservice.data.repository.OrderRepository;
import site.itxia.apiservice.data.repository.OrderUploadRepository;
import site.itxia.apiservice.dto.OrderDTO;
import site.itxia.apiservice.dto.OrderHistoryDTO;
import site.itxia.apiservice.enumable.OrderAction;
import site.itxia.apiservice.enumable.OrderStatus;
import site.itxia.apiservice.mapper.OrderHistoryMapper;
import site.itxia.apiservice.mapper.OrderMapper;
import site.itxia.apiservice.util.DateUtil;
import site.itxia.apiservice.vo.HandleOrderVo;
import site.itxia.apiservice.vo.RequestOrderVo;

import java.util.ArrayList;
import java.util.Comparator;
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
    @Autowired
    private OrderUploadRepository orderUploadRepository;

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

        //保存附件信息
        for (int uploadID : requestOrderVo.getAttachments()) {
            var entity = OrderUpload.builder()
                    .orderID(orderID)
                    .uploadID(uploadID)
                    .delete(false)
                    .build();
            orderUploadRepository.save(entity);
        }

        //TODO 保存标签信息

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
        dto.setHistory(getOrderHistoryDTOByOrderID(dto.getId()));
        //添加处理人
        var handleList = orderHistoryRepository.getAllByOrderID(id);
        if (handleList.size() > 0) {
            //按时间排序，查找历史
            //TODO 检查排序顺序
            handleList.sort((o1, o2) -> o1.getTime() - o2.getTime());
            Integer handlerID = null;
            for (var handle : handleList) {
                if (handle.getAction() == OrderAction.ACCEPT) {
                    handlerID = handle.getMemberID();
                } else if (handlerID != null && handlerID == handle.getMemberID() && handle.getAction() == OrderAction.PUT_BACK) {
                    handlerID = null;
                }
            }
            if (handlerID != null) {
                var handler = memberRepository.findById(handlerID);
                if (handler.isPresent()) {
                    dto.setHandlerID(handlerID);
                    dto.setHandlerName(handler.get().getRealName());
                }
            }
        }
        return dto;
    }

    /**
     * 获取预约单。(全部)
     * TODO 改成pageable
     *
     * @return 预约单列表.
     */
    public List<OrderDTO> getAllOrder() {
        var orderList = orderRepository.findAll();
        var dtoList = new ArrayList<OrderDTO>();
        for (Order order : orderList) {
            var dto = orderMapper.orderToOrderDTO(order);
            dto.setHistory(getOrderHistoryDTOByOrderID(dto.getId()));
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * @return 更新后的预约单dto.
     */
    public OrderDTO handleOrder(int memberID, HandleOrderVo handleOrderVo) {
        //TODO 验证预约单存在、预约单状态

        var orderID = handleOrderVo.getOrderID();


        //保存历史记录
        var orderHistory = orderHistoryMapper.handleToOrderHistory(handleOrderVo);
        orderHistory.setMemberID(memberID);
        orderHistory.setTime(DateUtil.getCurrentUnixTime());
        orderHistoryRepository.save(orderHistory);

        //更新预约单(状态、处理人)
        updateOrderStatus(orderID, handleOrderVo.getAction());

        return getOrder(orderID);
    }

    /**
     * TODO 错误状态判断
     * TODO 使用枚举代替硬编码数字
     */
    private void updateOrderStatus(int orderID, int action) {
        OrderStatus newState;
        var order = orderRepository.findById(orderID).get();
        //憨批java语法，enum.int不能用在switch
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
        orderRepository.save(order);
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
