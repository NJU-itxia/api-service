package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.mapper.MemberMapper;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.util.PasswordUtil;
import site.itxia.apiservice.vo.MemberAddVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberDTO> getAllMember() {
        var memberDTOList = new ArrayList<MemberDTO>();
        for (var member : memberRepository.findAll()) {
            memberDTOList.add(MemberMapper.MAPPER.memberToMemberDTO(member));
        }
        return memberDTOList;
    }

    public ResultWrapper addNewMember(MemberAddVo memberAddVo) {
        var po = MemberMapper.MAPPER.voToPo(memberAddVo);
        po.setPassword(PasswordUtil.encrypt(po.getPassword())); //加密密码
        var result = memberRepository.save(po);
        return ResultWrapper.wrapSuccess(MemberMapper.MAPPER.memberToMemberDTO(result));
    }
}
