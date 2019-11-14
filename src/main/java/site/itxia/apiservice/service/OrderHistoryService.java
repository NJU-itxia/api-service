package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.OrderHistory;
import site.itxia.apiservice.data.repository.OrderHistoryRepository;
import site.itxia.apiservice.dto.OrderHistoryDTO;
import site.itxia.apiservice.enumable.OrderAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenxi
 */
@Service
public class OrderHistoryService {


    private OrderHistoryRepository orderHistoryRepository;
    private MemberService memberService;

    @Autowired
    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository, MemberService memberService) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.memberService = memberService;
    }


    /**
     * 获取预约单的全部历史记录.
     *
     * @param orderID 预约单id.
     * @return 历史记录列表.
     */
    public List<OrderHistoryDTO> getOrderHistoryDTOByOrderID(int orderID) {
        var orderHistoryDTOList = new ArrayList<OrderHistoryDTO>();
        for (OrderHistory entity : orderHistoryRepository.getAllByOrderID(orderID)) {
            orderHistoryDTOList.add(entityToDto(entity));
        }
        return orderHistoryDTOList;
    }

    /**
     * 从历史记录计算接单人.
     * @param orderID 预约单id
     * @return 接单人id, null表示还没人接单
     */
    public Integer getHandlerByOrderID(int orderID) {
        Integer handlerMemberID = null;
        for (OrderHistory history : orderHistoryRepository.getAllByOrderIDOrderByTimeAsc(orderID)) {
            switch (history.getAction()) {
                case ACCEPT:
                    if (handlerMemberID == null) {
                        handlerMemberID = history.getMemberID();
                    } else {
                        //TODO log不可能的情况
                    }
                    break;
                case PUT_BACK:
                    if (handlerMemberID == history.getMemberID()) {
                        handlerMemberID = null;
                    } else {
                        //TODO log不可能的情况
                    }
                    break;
                case CANCEL:
                    break;
                case FINISH:
                    break;
                case ABANDON:
                    break;
            }
        }
        return handlerMemberID;
    }

    /**
     * entity转dto.
     */
    private OrderHistoryDTO entityToDto(OrderHistory entity) {
        return OrderHistoryDTO.builder()
                .action(entity.getAction().getAction())
                .memberID(entity.getMemberID())
                .memberName(memberService.getMemberNameByID(entity.getMemberID()))
                .time(entity.getTime())
                .build();
    }
}
