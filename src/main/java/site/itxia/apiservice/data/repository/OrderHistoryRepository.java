package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.OrderHistory;

public interface OrderHistoryRepository extends CrudRepository<OrderHistory, Integer> {
}
