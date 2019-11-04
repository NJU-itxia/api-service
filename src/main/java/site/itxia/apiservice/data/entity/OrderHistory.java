package site.itxia.apiservice.data.entity;

import lombok.Data;
import site.itxia.apiservice.enumable.OrderAction;

import javax.persistence.*;

/**
 * @author zhenxi
 */
@Data
@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id", nullable = false)
    private int orderID;

    @Column(name = "member_id", nullable = false)
    private int memberID;

    @Column(nullable = false, columnDefinition = "bit(3)")
    @Enumerated(value = EnumType.ORDINAL)
    private OrderAction action;

    @Column(nullable = false)
    private int time;
}
