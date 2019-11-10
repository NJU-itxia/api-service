package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.OrderTag;

public interface OrderTagRepository extends CrudRepository<OrderTag,Integer> {
}
