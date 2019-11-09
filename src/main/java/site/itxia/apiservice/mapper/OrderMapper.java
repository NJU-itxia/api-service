package site.itxia.apiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.itxia.apiservice.data.entity.Order;
import site.itxia.apiservice.dto.OrderDTO;
import site.itxia.apiservice.vo.RequestOrderVo;

/**
 * @author zhenxi
 */
@Mapper(uses = EnumableMapper.class)
public interface OrderMapper {

    public static OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "customerName")
    @Mapping(source = "phone", target = "customerPhone")
    @Mapping(source = "qq", target = "customerQQ")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "summary", ignore = true)
    @Mapping(target = "time", ignore = true)
    public Order requestOrderVoToOrder(RequestOrderVo requestOrderVo);


    @Mapping(source = "status.status", target = "status")
    @Mapping(target = "history", ignore = true)
    public OrderDTO orderToOrderDTO(Order order);
}
