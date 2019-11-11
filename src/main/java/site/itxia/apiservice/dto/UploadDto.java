package site.itxia.apiservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhenxi
 */
@Builder
@Data
public class UploadDto {

    private int id;

    private int uploadByMemberID;

    private int uploadByMemberName;

    private long size;

    private String sha256sum;

    private int time;

}
