package site.itxia.apiservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhenxi
 */
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "`delete`", nullable = false)
    private boolean delete;

}
