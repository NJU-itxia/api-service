package site.itxia.apiservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.itxia.apiservice.enumable.Campus;
import site.itxia.apiservice.enumable.MemberRole;
import site.itxia.apiservice.enumable.MemberStatus;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "`member`")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "login_name", nullable = false, unique = true)
    private String loginName;

    @Column(name = "`password`", nullable = false)
    private String password;

    @Column(name = "`campus`", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private Campus campus;

    @Column(name = "`role`", nullable = false, columnDefinition = "bit(2) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private MemberRole role;

    @Column(name = "`status`", nullable = false, columnDefinition = "bit(1) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private MemberStatus status;

}
