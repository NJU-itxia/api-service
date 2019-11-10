package site.itxia.apiservice.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * @author zhenxi
 */
@Builder
@Data
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tag_name", nullable = false)
    private String tagName;

    @Column(name = "add_by", nullable = false)
    private int addByMemberID;

    @Column(name = "add_time", nullable = false)
    private int addTime;

    @Column(nullable = false)
    private boolean delete;

}
