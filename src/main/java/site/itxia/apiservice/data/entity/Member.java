package site.itxia.apiservice.data.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private int role;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private int status;

}
