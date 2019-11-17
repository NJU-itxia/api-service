package site.itxia.apiservice.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author zhenxi
 */
@Data
public class ChangMemberStatusVo {
    @Range(min = 1, max = 2)
    private int newStatus;
}
