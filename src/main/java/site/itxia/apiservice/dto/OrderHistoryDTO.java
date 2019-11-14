package site.itxia.apiservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhenxi
 */
@Builder
@Data
public class OrderHistoryDTO {

    private int memberID;

    private String memberName;

    private int action;

    private int time;
}
