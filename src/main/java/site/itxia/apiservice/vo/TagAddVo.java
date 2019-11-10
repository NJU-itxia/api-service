package site.itxia.apiservice.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhenxi
 */
@Data
public class TagAddVo {

    @NotBlank
    private String tagName;

}
