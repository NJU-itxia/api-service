package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.Order;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    boolean existsByToken(String token);

}
