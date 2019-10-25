package site.itxia.apiservice.data.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    public Member(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String real_name;

    private String login_name;

    private String password;

    private int role;

    private int status;

}
