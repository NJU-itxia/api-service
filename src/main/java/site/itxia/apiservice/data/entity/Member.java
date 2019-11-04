package site.itxia.apiservice.data.entity;

import lombok.Data;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.enumable.MemberStatus;

import javax.persistence.*;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "login_name", nullable = false)
    private String loginName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "bit(2) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private MemberRole role;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private MemberStatus status;

}
