package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.Member;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.enumable.ErrorCode;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.exception.ItxiaRuntimeException;
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

        //检查登录名是否已存在
        var optional = memberRepository.findByLoginName(memberAddVo.getLoginName());
        if (optional.isPresent()) {
            throw new ItxiaRuntimeException(ErrorCode.LOGIN_NAME_ALREADY_EXISTS);
        }

        //保存新账号
        var result = memberRepository.save(po);
        return ResultWrapper.wrapSuccess(MemberMapper.MAPPER.memberToMemberDTO(result));
    }

    /**
     * @param memberID 成员ID
     * @return 成员姓名. 如果为null表示找不到这个ID.
     */
    public String getMemberNameByID(Integer memberID) {
        if (memberID == null) {
            return "";
        } else if (memberID == 0) {
            return "访客";
        }
        return getMember(memberID).getRealName();
    }

    public MemberRole getMemberRole(int memberID) {
        return getMember(memberID).getRole();
    }

    private Member getMember(int memberID) {
        var optional = memberRepository.findById(memberID);
        if (optional.isEmpty()) {
            throw new ItxiaRuntimeException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return optional.get();
    }

    public MemberDTO getMemberByID(int memberID) {
        return MemberMapper.MAPPER.memberToMemberDTO(getMember(memberID));
    }
}
