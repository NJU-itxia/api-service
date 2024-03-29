package site.itxia.apiservice.data.repository;

import org.springframework.data.repository.CrudRepository;
import site.itxia.apiservice.data.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Integer> {

    List<Member> findAll();

    Optional<Member> findByLoginName(String loginName);

}
