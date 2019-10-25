package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.data.entity.Member;
import site.itxia.apiservice.data.repository.MemberRepository;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberRepository memberRepository;

    @Autowired
    public MemberController(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @GetMapping("")
    public List<Member> getAll(){
        return memberRepository.findAll();
    }

    @PostMapping("")
    public String add(@RequestBody Member member){
        memberRepository.save(member);
        return "succ";
    }


}
