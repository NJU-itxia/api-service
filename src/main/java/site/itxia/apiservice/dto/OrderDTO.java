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

    private int location;

    private String description;

    private int status;

    private String summary;

    private int time;

    /**
     * 额外信息
     * */
    private List<OrderHistoryDTO> orderHistoryList;


}
