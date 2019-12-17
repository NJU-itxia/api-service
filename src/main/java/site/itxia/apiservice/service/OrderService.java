package site.itxia.apiservice.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.Order;
import site.itxia.apiservice.data.entity.OrderHistory;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.data.repository.OrderHistoryRepository;
import site.itxia.apiservice.data.repository.OrderRepository;
import site.itxia.apiservice.dto.OrderDTO;
import site.itxia.apiservice.enumable.*;
import site.itxia.apiservice.exception.ItxiaRuntimeException;
import site.itxia.apiservice.util.DateUtil;
import site.itxia.apiservice.util.TokenUtil;
import site.itxia.apiservice.vo.HandleOrderVo;
import site.itxia.apiservice.vo.RequestOrderVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenxi
 */
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private MemberRepository memberRepository;
    private OrderHistoryRepository orderHistoryRepository;
    private TagService tagService;
    private UploadService uploadService;
    private MemberService memberService;
    private OrderHistoryService orderHistoryService;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository,
                        OrderHistoryRepository orderHistoryRepository, TagService tagService,
                        UploadService uploadService, MemberService memberService,
                        OrderHistoryService orderHistoryService) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.orderHistoryRepository = orderHistoryRepository;
        this.tagService = tagService;
        this.uploadService = uploadService;
        this.memberService = memberService;
        this.orderHistoryService = orderHistoryService;
    }

    /**
     * 收到新的预约单.
     *
     * @param vo 预约单表格信息.
     * @return 预约单.(包含ID)
     */
    public OrderDTO addOrder(RequestOrderVo vo) {
        var order = Order.builder()
                .customerName(vo.getName())
                .customerPhone(vo.getPhone())
                .customerEmail(vo.getEmail())
                .model(vo.getModel())
                .warranty(OrderWarranty.from(vo.getWarranty()))
                .campus(Campus.from(vo.getCampus()))
                .description(vo.getDescription())
                .status(OrderStatus.CREATED)
                .summary(null)
                .time(DateUtil.getCurrentUnixTime())
                .token(TokenUtil.generateToken())
                .build();
        //保存到数据库中
        var savedOrder = orderRepository.save(order);
        var orderID = savedOrder.getId();

        //保存标签信息, memberID 0 表示游客
        tagService.attachTagsToOrder(0, orderID, vo.getTags());

        //保存附件信息
        uploadService.attachUploadsToOrder(orderID, vo.getAttachments());

        return getOrderDto(orderID);
    }

    /**
     * 获取预约单(单个).
     *
     * @param orderID 预约单id
     * @return 预约单dto
     */
    public OrderDTO getOrderDto(int orderID) {
        return toOrderDto(getOrderEntity(orderID));
    }

    /**
     * 获取Order entity.
     *
     * @param orderID 预约单id
     * @return 预约单entity
     * @throws ItxiaRuntimeException 找不到预约单时抛出
     */
    private Order getOrderEntity(int orderID) {
        var optional = orderRepository.findById(orderID);
        if (optional.isEmpty()) {
            throw new ItxiaRuntimeException(ErrorCode.ORDER_NOT_FOUND);
        }
        return optional.get();
    }

    /**
     * 获取预约单dto.
     *
     * @param entity 预约单entity
     * @return 预约单dto
     */
    private OrderDTO toOrderDto(Order entity) {
        var orderID = entity.getId();

        //标签
        var tags = tagService.getTagDtosByOrderID(orderID);

        //附件
        var attachments = uploadService.getUploadDtosByOrderID(orderID);

        //接单历史
        var history = orderHistoryService.getOrderHistoryDTOByOrderID(orderID);

        //处理人
        var handlerID = orderHistoryService.getHandlerByOrderID(orderID);
        var handlerName = memberService.getMemberNameByID(handlerID);

        return OrderDTO.builder()
                .id(entity.getId())
                .customerName(entity.getCustomerName())
                .customerPhone(entity.getCustomerPhone())
                .customerEmail(entity.getCustomerEmail())
                .model(entity.getModel())
                .warranty(entity.getWarranty().getWarranty())
                .campus(entity.getCampus().getLocation())
                .description(entity.getDescription())
                .status(entity.getStatus().getStatus())
                .summary(entity.getSummary())
                .time(entity.getTime())
                .tags(tags)
                .attachments(attachments)
                .handlerID(handlerID)
                .handlerName(handlerName)
                .history(history)
                .token(entity.getToken())
                .build();
    }

    /**
     * 获取预约单(全部).
     * TODO 改成pageable
     *
     * @return 预约单列表.
     */
    public List<OrderDTO> getAllOrderDto() {
        var orderList = orderRepository.findAll();
        var dtoList = new ArrayList<OrderDTO>();
        for (Order entity : orderList) {
            dtoList.add(toOrderDto(entity));
        }
        return dtoList;
    }

    /**
     * 处理接单.
     *
     * @param memberID 成员id
     * @param vo       接单信息vo
     * @return 更新后的预约单dto
     */
    public OrderDTO handleOrder(int memberID, HandleOrderVo vo) {
        @AllArgsConstructor
        @Getter
        class StateTransfer {
            private OrderStatus oldStatus;      //预约单旧状态
            private OrderAction action;         //预约单处理动作
            private OrderStatus newStatus;      //预约单新状态
            private MemberRole requiredRole;    //需要的最小权限

            /**
             * @param status 当前状态
             * @param action 将要进行的动作
             * @return 是否可以进行该动作
             * */
            private boolean canDoAction(OrderStatus status, int action) {
                return status == this.oldStatus
                        && action == this.action.getAction();
            }

            /**
             * @param role 成员角色
             * @return 是否有权限执行该动作
             * */
            private boolean hasPermission(MemberRole role) {
                return role.getRole() >= this.requiredRole.getRole();
            }
        }
        //状态转移表 (少写了很多if else)
        StateTransfer[] stateTransfers = {
                new StateTransfer(OrderStatus.CREATED, OrderAction.ACCEPT, OrderStatus.ACCEPTED, MemberRole.MEMBER),
                new StateTransfer(OrderStatus.CREATED, OrderAction.CANCEL, OrderStatus.CANCELED, MemberRole.GUEST),
                new StateTransfer(OrderStatus.CREATED, OrderAction.ABANDON, OrderStatus.ABANDONED, MemberRole.ADMIN),

                new StateTransfer(OrderStatus.ACCEPTED, OrderAction.PUT_BACK, OrderStatus.CREATED, MemberRole.MEMBER),
                new StateTransfer(OrderStatus.ACCEPTED, OrderAction.FINISH, OrderStatus.COMPLETED, MemberRole.MEMBER),
                new StateTransfer(OrderStatus.ACCEPTED, OrderAction.CANCEL, OrderStatus.CANCELED, MemberRole.GUEST),
                new StateTransfer(OrderStatus.ACCEPTED, OrderAction.ABANDON, OrderStatus.ABANDONED, MemberRole.ADMIN),
        };

        //获取预约单entity
        var orderID = vo.getOrderID();
        var entity = getOrderEntity(orderID);
        var oldStatus = entity.getStatus();
        var action = vo.getAction();
        var role = memberService.getMemberRole(memberID);

        //寻找符合的状态转移
        StateTransfer stateTransfer = null;
        for (var transfer : stateTransfers) {
            if (transfer.canDoAction(oldStatus, action)) {
                if (transfer.hasPermission(role)) {
                    stateTransfer = transfer;
                } else {
                    //无权限执行该动作
                    throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
                }
                break;
            }
        }
        if (stateTransfer == null) {
            //没有符合的动作
            throw new ItxiaRuntimeException(ErrorCode.ACTION_NOT_MATCH_ORDER);
        }

        //更新预约单状态
        entity.setStatus(stateTransfer.getNewStatus());
        orderRepository.save(entity);

        //保存历史记录
        var orderHistory = OrderHistory.builder()
                .orderID(orderID)
                .action(OrderAction.from(vo.getAction()))
                .memberID(memberID)
                .time(DateUtil.getCurrentUnixTime())
                .build();
        orderHistoryRepository.save(orderHistory);

        return getOrderDto(orderID);
    }
}
