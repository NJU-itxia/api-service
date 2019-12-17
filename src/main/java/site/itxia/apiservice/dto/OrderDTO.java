package site.itxia.apiservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zhenxi
 * <p>
 * 预约单信息
 */
@Builder
@Data
public class OrderDTO {

    private int id;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

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
    private Integer handlerID;

    /**
     * 处理人姓名.
     */
    private String handlerName;

    /**
     * 标签列表.
     */
    private List<TagDto> tags;

    /**
     * 附件列表.
     */
    private List<UploadDto> attachments;

    /**
     * 预约人的access token.
     * */
    private String token;
}
