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

    @Column(name = "real_name")
    private String realName;

    @Column(name = "login_name")
    private String loginName;

    private String password;

    private int role;

    private int status;

}
