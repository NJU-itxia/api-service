package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.service.MemberService;
import site.itxia.apiservice.vo.ChangMemberStatusVo;
import site.itxia.apiservice.vo.MemberAddVo;

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
                                            @Valid @RequestBody ChangMemberStatusVo vo) {
        return memberService.updateMemberStatus(toChangeMemberID, requestMemberID, vo);
    }

}
