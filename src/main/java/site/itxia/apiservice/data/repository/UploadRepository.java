package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.Upload;

public interface UploadRepository extends CrudRepository<Upload, Integer> {
}
