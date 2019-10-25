package site.itxia.apiservice.controller;

import net.minidev.json.JSONObject;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.data.entity.Member;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.util.PasswordUtil;
import site.itxia.apiservice.vo.MemberAddVo;

import javax.validation.Valid;
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
    public String add(@Valid @RequestBody MemberAddVo memberAddVo){
        //var pwd =jsonObject.get("password");
        //System.out.println("password:"+pwd);
        //memberRepository.save(member);
        return "succ";
    }


}
