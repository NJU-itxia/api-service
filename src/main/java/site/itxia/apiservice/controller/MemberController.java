package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.service.MemberService;
import site.itxia.apiservice.vo.MemberAddVo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public List<MemberDTO> getAll(){
        return memberService.getAllMember();
    }

    @PostMapping("")
    public ResultWrapper<MemberDTO> add(@Valid @RequestBody MemberAddVo memberAddVo){
        return memberService.addNewMember(memberAddVo);
    }


}
