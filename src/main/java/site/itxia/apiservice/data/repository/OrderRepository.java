package site.itxia.apiservice.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.Order;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    boolean existsByToken(String token);

    Page<Order> findAll(Pageable pageable);

}
