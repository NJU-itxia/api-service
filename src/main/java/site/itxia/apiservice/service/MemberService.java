package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.enumable.ErrorCode;
import site.itxia.apiservice.mapper.MemberMapper;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.util.PasswordUtil;
import site.itxia.apiservice.vo.MemberAddVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
        try {
            var result = memberRepository.save(po);
            return ResultWrapper.wrapSuccess(MemberMapper.MAPPER.memberToMemberDTO(result));
        } catch (DataIntegrityViolationException e) {
            //loginName重复
            return ResultWrapper.wrap(ErrorCode.MEMBER_ALREADY_EXISTS);
        }
    }

    /**
     * @param memberID 成员ID
     * @return 成员姓名. 如果为null表示找不到这个ID.
     */
    public String getMemberNameByID(int memberID) {
        if (memberID == 0) {
            return "访客";
        }
        var member = memberRepository.findById(memberID);
        if (member == null) {
            return "查无此人";
        }
        return member.getRealName();
    }

    public MemberDTO getMemberByID(int memberID) {
        return MemberMapper.MAPPER.memberToMemberDTO(memberRepository.findById(memberID));
    }
}
