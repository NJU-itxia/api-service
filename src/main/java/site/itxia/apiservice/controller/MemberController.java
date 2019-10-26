package site.itxia.apiservice.controller;

import net.minidev.json.JSONObject;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.data.entity.Member;
import site.itxia.apiservice.data.repository.MemberRepository;
import site.itxia.apiservice.dto.MemberDTO;
import site.itxia.apiservice.dto.MemberMapper;
import site.itxia.apiservice.util.PasswordUtil;
import site.itxia.apiservice.vo.MemberAddVo;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public List<MemberDTO> getAll(){
        var memberDTOList = new ArrayList<MemberDTO>();
        for(var member : memberRepository.findAll()){
            memberDTOList.add(MemberMapper.MAPPER.memberToMemberDTO(member));
        }
        return memberDTOList;
    }

    @PostMapping("")
    public String add(@Valid @RequestBody MemberAddVo memberAddVo){
        var po = MemberMapper.MAPPER.voToPo(memberAddVo);
        memberRepository.save(po);
        //var pwd =jsonObject.get("password");
        //System.out.println("password:"+pwd);
        //memberRepository.save(member);
        return "succ";
    }


}
