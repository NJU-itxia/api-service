package site.itxia.apiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import site.itxia.apiservice.data.entity.Member;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.vo.MemberAddVo;

@Mapper(uses = EnumableMapper.class)
public interface MemberMapper {

    public static MemberMapper MAPPER = Mappers.getMapper(MemberMapper.class);

    MemberDTO memberToMemberDTO(Member member);

    @Mapping(target = "id", ignore = true)
    Member voToPo(MemberAddVo memberAddVo);
}
