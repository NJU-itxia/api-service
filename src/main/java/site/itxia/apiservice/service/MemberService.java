package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.Member;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.enumable.Campus;
import site.itxia.apiservice.enumable.ErrorCode;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.enumable.MemberStatus;
import site.itxia.apiservice.exception.ItxiaRuntimeException;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.util.PasswordUtil;
import site.itxia.apiservice.vo.*;

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
        for (var entity : memberRepository.findAll()) {
            memberDTOList.add(entityToDto(entity));
        }
        return memberDTOList;
    }

    public MemberDTO getMemberDtoByID(int memberID) {
        return entityToDto(getMember(memberID));
    }

    public ResultWrapper addNewMember(MemberAddVo vo) {
        //检查登录名是否已存在
        var optional = memberRepository.findByLoginName(vo.getLoginName());
        if (optional.isPresent()) {
            throw new ItxiaRuntimeException(ErrorCode.LOGIN_NAME_ALREADY_EXISTS);
        }

        //保存新账号
        var entity = Member.builder()
                .realName(vo.getRealName())
                .loginName(vo.getLoginName())
                .password(PasswordUtil.encrypt(vo.getPassword()))
                .campus(Campus.from(vo.getCampus()))
                .role(MemberRole.from(vo.getRole()))
                .status(MemberStatus.from(vo.getStatus()))
                .build();
        var savedEntity = memberRepository.save(entity);
        return ResultWrapper.wrapSuccess(entityToDto(savedEntity));
    }

    /**
     * TODO 更新个人信息的重复代码太多了,封装一下...
     * 更改账号的状态(启用/禁用).
     */
    public ResultWrapper updateMemberStatus(int toChangeMemberID, Integer requestMemberID, ChangeMemberStatusVo vo) {
        //检查请求的身份权限，必须为管理员
        if (requestMemberID == null || getMemberRole(requestMemberID) != MemberRole.ADMIN) {
            throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
        }
        var entity = getMember(toChangeMemberID);
        entity.setStatus(MemberStatus.from(vo.getStatus()));
        memberRepository.save(entity);
        return ResultWrapper.wrapSuccess(null);
    }

    /**
     * 更改账号的角色(成员/管理员).
     */
    public ResultWrapper updateMemberRole(int toChangeMemberID, Integer requestMemberID, ChangeMemberRoleVo vo) {
        //检查请求的身份权限，必须为管理员
        if (requestMemberID == null || getMemberRole(requestMemberID) != MemberRole.ADMIN) {
            throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
        }
        var entity = getMember(toChangeMemberID);
        entity.setRole(MemberRole.from(vo.getRole()));
        memberRepository.save(entity);
        return ResultWrapper.wrapSuccess(null);
    }

    /**
     * 更改账号的密码.
     */
    public ResultWrapper updateMemberPassword(int toChangeMemberID, Integer requestMemberID, MemberPasswordResetVo vo) {
        //检查请求的身份权限,必须为管理员,也可以是自己改自己的
        if (requestMemberID == null) {
            throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
        }
        if (toChangeMemberID != requestMemberID && getMemberRole(requestMemberID) != MemberRole.ADMIN) {
            throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
        }
        var entity = getMember(toChangeMemberID);
        entity.setPassword(PasswordUtil.encrypt(vo.getPassword()));
        memberRepository.save(entity);
        return ResultWrapper.wrapSuccess(null);
    }

    /**
     * 更改账号的个人信息.
     */
    public ResultWrapper updateMemberInfo(int toChangeMemberID, Integer requestMemberID, ChangeMemberInfoVo vo) {
        //检查请求的身份权限,必须为管理员,也可以是自己改自己的
        if (requestMemberID == null) {
            throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
        }
        if (toChangeMemberID != requestMemberID && getMemberRole(requestMemberID) != MemberRole.ADMIN) {
            throw new ItxiaRuntimeException(ErrorCode.UNAUTHORIZED);
        }
        var entity = getMember(toChangeMemberID);
        entity.setCampus(Campus.from(vo.getCampus()));
        memberRepository.save(entity);
        return ResultWrapper.wrapSuccess(null);
    }

    private MemberDTO entityToDto(Member entity) {
        return MemberDTO.builder()
                .id(entity.getId())
                .loginName(entity.getLoginName())
                .realName(entity.getRealName())
                .campus(entity.getCampus().getLocation())
                .role(entity.getRole().getRole())
                .status(entity.getStatus().getStatus())
                .build();
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
}
