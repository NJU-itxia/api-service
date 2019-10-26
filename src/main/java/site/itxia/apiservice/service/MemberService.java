package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.dto.MemberMapper;
import site.itxia.apiservice.vo.MemberAddVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<MemberDTO> getAllMember(){
        var memberDTOList = new ArrayList<MemberDTO>();
        for(var member : memberRepository.findAll()){
            memberDTOList.add(MemberMapper.MAPPER.memberToMemberDTO(member));
        }
        return memberDTOList;
    }

    public MemberDTO addNewMember(MemberAddVo memberAddVo){
        var po = MemberMapper.MAPPER.voToPo(memberAddVo);
        var result = memberRepository.save(po);
        return MemberMapper.MAPPER.memberToMemberDTO(result);
        //var member = new Member(0,realName,loginName,password,role,status);
    }
}
