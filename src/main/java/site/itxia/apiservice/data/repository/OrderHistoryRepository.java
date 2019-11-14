package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryRepository extends CrudRepository<OrderHistory, Integer> {

    List<OrderHistory> getAllByOrderID(int orderID);

    List<OrderHistory> getAllByOrderIDOrderByTimeAsc(int orderID);

}
