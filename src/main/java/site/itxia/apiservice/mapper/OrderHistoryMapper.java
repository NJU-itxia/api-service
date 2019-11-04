package site.itxia.apiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.itxia.apiservice.data.entity.Order;
import site.itxia.apiservice.data.entity.OrderHistory;
import site.itxia.apiservice.dto.OrderDTO;
import site.itxia.apiservice.dto.OrderHistoryDTO;
import site.itxia.apiservice.enumable.OrderAction;
import site.itxia.apiservice.vo.HandleOrderVo;
import site.itxia.apiservice.vo.RequestOrderVo;

/**
 * @author zhenxi
 */
@Mapper(uses = EnumableMapper.class)
public interface OrderHistoryMapper {

    public static OrderHistoryMapper INSTANCE = Mappers.getMapper(OrderHistoryMapper.class);

    @Mapping(target = "memberName", ignore = true)
    @Mapping(source = "action.action", target = "action")
    public OrderHistoryDTO toDTO(OrderHistory orderHistory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberID", ignore = true)
    @Mapping(target = "time", ignore = true)
    public OrderHistory handleToOrderHistory(HandleOrderVo handleOrderVo);
}
