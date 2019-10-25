package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberAddVo {
    @NotBlank
    @Pattern(regexp = "^.{2,16}$",message = "歪果仁？")
    private String realName;
    @NotBlank
    @Pattern(regexp = "^\\w{4,16}$",message = "账号名要求：4-16位数字、字母组合")
    private String loginName;
    @NotBlank
    @Pattern(regexp = "^\\w{8,16}$",message = "密码要求：8-16位数字、字母组合")
    private String password;
    @Range(min = 0, max = 2,message = "角色参数错误")
    private int role;
    @Range(min = 0, max = 1,message = "状态码错误")
    private int status;
}
