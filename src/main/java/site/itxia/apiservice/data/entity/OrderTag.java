package site.itxia.apiservice.data.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zhenxi
 */
@Data
@Entity
@Table(name = "order_tag")
public class OrderTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id", nullable = false)
    private int orderID;

    @Column(name = "tag_id", nullable = false)
    private int tagID;

    @Column(name = "add_by", nullable = false)
    private int addByMemberID;

    @Column(name = "add_time", nullable = false)
    private int addTime;

    @Column(name = "`delete`", nullable = false)
    private boolean delete;
}
