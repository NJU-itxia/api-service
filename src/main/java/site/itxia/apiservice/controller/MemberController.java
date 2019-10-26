package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.service.MemberService;
import site.itxia.apiservice.vo.MemberAddVo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("")
    public List<MemberDTO> getAll(){
        return memberService.getAllMember();
    }

    @PostMapping("")
    public MemberDTO add(@Valid @RequestBody MemberAddVo memberAddVo){
        return memberService.addNewMember(memberAddVo);
    }


}
