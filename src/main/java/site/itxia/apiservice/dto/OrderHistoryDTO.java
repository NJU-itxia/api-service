package site.itxia.apiservice.dto;

import lombok.Data;

/**
 * @author zhenxi
 */
@Data
public class OrderHistoryDTO {

    private int memberID;

    private String memberName;

    private int action;

    private int time;
}
