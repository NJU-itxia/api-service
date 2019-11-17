package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author zhenxi
 */
@Data
public class ChangeMemberStatusVo {
    @Range(min = 0, max = 1)
    private int status;
}
