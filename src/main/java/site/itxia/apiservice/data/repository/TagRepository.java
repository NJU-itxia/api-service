package site.itxia.apiservice.data.repository;


import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.Tag;

public interface TagRepository extends CrudRepository<Tag,Integer> {
}
