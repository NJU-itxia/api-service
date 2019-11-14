package site.itxia.apiservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberDTO {
    private int id;
    private String realName;
    private String loginName;
    private int role;
    private int status;
}
