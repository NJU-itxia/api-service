package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.OrderUpload;

import java.util.List;

public interface OrderUploadRepository extends CrudRepository<OrderUpload, Integer> {

    List<OrderUpload> findByOrderID(int orderID);

}
