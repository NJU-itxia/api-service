package site.itxia.apiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.itxia.apiservice.data.entity.Order;
import site.itxia.apiservice.data.entity.OrderHistory;
import site.itxia.apiservice.dto.OrderDTO;
import site.itxia.apiservice.dto.OrderHistoryDTO;
import site.itxia.apiservice.vo.HandleOrderVo;
import site.itxia.apiservice.vo.RequestOrderVo;

/**
 * @author zhenxi
 */
@Mapper
public interface OrderHistoryMapper {

    public static OrderHistoryMapper INSTANCE = Mappers.getMapper(OrderHistoryMapper.class);

    public OrderHistoryDTO toDTO(OrderHistory orderHistory);

    public OrderHistory handleToOrderHistory(HandleOrderVo handleOrderVo);
}
