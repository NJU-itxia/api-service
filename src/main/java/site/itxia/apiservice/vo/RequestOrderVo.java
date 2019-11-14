package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 标签(ID)数组.
     */
    private List<Integer> tags = new ArrayList<>();

    /**
     * 附件(ID)数组.
     */
    private List<Integer> attachments = new ArrayList<>();
}
