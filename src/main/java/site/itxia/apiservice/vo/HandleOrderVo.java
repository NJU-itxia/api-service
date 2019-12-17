package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author zhenxi
 */
@Data
public class HandleOrderVo {

    @Range(min = 0)
    int orderID;

    @Range(min = 0, max = 4)
    int action;

    String token;
}
