package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
 * @author zhenxi
 */
@Data
public class HandleOrderVo {

    int orderID;

    @Range(min = 0, max = 4)
    int action;

}
