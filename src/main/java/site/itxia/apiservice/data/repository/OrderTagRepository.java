package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.OrderTag;

import java.util.List;

public interface OrderTagRepository extends CrudRepository<OrderTag,Integer> {

    List<OrderTag> findByOrderID(int orderID);

}
