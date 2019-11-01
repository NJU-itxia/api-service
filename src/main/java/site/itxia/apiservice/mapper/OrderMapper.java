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
@Mapper
public interface OrderMapper {

    public static OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "name", target = "customerName")
    @Mapping(source = "phone", target = "customerPhone")
    @Mapping(source = "qq", target = "customerQQ")
    public Order requestOrderVoToOrder(RequestOrderVo requestOrderVo);


    @Mapping(source = "status.status", target = "status")
    public OrderDTO orderToOrderDTO(Order order);
}
