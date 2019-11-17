package site.itxia.apiservice.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author zhenxi
 */
@Data
public class MemberPasswordResetVo {
    @NotBlank
    @Pattern(regexp = "^\\w{8,16}$",message = "密码要求：8-16位数字、字母组合")
    private String password;
}
