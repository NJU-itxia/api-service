package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author zhenxi
 */
@Data
public class RequestOrderVo {

    @NotBlank
    private String name;

    private String phone;

    private String qq;

    private String model;

    @Range(min = 0, max = 2)
    private int warranty;

    @Range(min = 1, max = 2)    //0为全部校区
    private int campus;

    private String description;

}
