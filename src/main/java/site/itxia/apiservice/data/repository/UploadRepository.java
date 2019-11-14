package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.Upload;

import java.util.Optional;

public interface UploadRepository extends CrudRepository<Upload, Integer> {

    Optional<Upload> findFirstBySha256sum(String sha256sum);

}
