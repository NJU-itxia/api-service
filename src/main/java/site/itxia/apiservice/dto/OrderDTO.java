package site.itxia.apiservice.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhenxi
 * <p>
 * 预约单信息
 */
@Data
public class OrderDTO {

    private int id;

    private String customerName;

    private String customerPhone;

    private String customerQQ;

    private String model;

    private int warranty;

    private int campus;

    private String description;

    private int status;

    private String summary;

    private int time;

    /**
     * 预约单操作历史.
     */
    private List<OrderHistoryDTO> history;

    /**
     * 处理人ID.
     */
    private int handlerID;

    /**
     * 处理人姓名.
     */
    private String handlerName;

}
