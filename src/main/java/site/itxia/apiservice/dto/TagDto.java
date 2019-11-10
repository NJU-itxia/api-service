package site.itxia.apiservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhenxi
 */
@Builder
@Data
public class TagDto {

    private int id;

    private String tagName;

    private int addByMemberID;

    private String addByMemberName;

    private int addTime;

    /**
     * 被预约单引用次数.
     */
    private int useCount;
}
