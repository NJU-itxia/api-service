package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.service.MemberService;
import site.itxia.apiservice.vo.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public ResultWrapper getSelf(@RequestHeader int memberID) {
        return ResultWrapper.wrapSuccess(memberService.getMemberDtoByID(memberID));
    }

    @GetMapping("/all")
    public ResultWrapper getAll() {
        return ResultWrapper.wrapSuccess(memberService.getAllMember());
    }

    @PostMapping("")
    public ResultWrapper add(@Valid @RequestBody MemberAddVo memberAddVo) {
        return memberService.addNewMember(memberAddVo);
    }

    @PutMapping("/{memberID:[0-9]{1,6}}/status")
    public ResultWrapper updateMemberStatus(@PathVariable(name = "memberID") int toChangeMemberID,
                                            @RequestHeader(name = "memberID") Integer requestMemberID,
                                            @Valid @RequestBody ChangeMemberStatusVo vo) {
        return memberService.updateMemberStatus(toChangeMemberID, requestMemberID, vo);
    }

    @PutMapping("/{memberID:[0-9]{1,6}}/role")
    public ResultWrapper updateMemberRole(@PathVariable(name = "memberID") int toChangeMemberID,
                                          @RequestHeader(name = "memberID") Integer requestMemberID,
                                          @Valid @RequestBody ChangeMemberRoleVo vo) {
        return memberService.updateMemberRole(toChangeMemberID, requestMemberID, vo);
    }

    @PutMapping("/{memberID:[0-9]{1,6}}/password")
    public ResultWrapper updateMemberPassword(@PathVariable(name = "memberID") int toChangeMemberID,
                                              @RequestHeader(name = "memberID") Integer requestMemberID,
                                              @Valid @RequestBody MemberPasswordResetVo vo) {
        return memberService.updateMemberPassword(toChangeMemberID, requestMemberID, vo);
    }

    @PutMapping("/{memberID:[0-9]{1,6}}/info")
    public ResultWrapper updateMemberInfo(@PathVariable(name = "memberID") int toChangeMemberID,
                                          @RequestHeader(name = "memberID") Integer requestMemberID,
                                          @Valid @RequestBody ChangeMemberInfoVo vo) {
        return memberService.updateMemberInfo(toChangeMemberID, requestMemberID, vo);
    }

}
