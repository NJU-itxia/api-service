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

    private String fileName;

    private int uploadByMemberID;

    private String uploadByMemberName;

    private long size;

    private String sha256sum;

    private int time;

}
