package site.itxia.apiservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.itxia.apiservice.enumable.OrderAction;

import javax.persistence.*;

/**
 * @author zhenxi
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "`action`", nullable = false, columnDefinition = "bit(3)")
    @Enumerated(value = EnumType.ORDINAL)
    private OrderAction action;

    @Column(name = "`time`", nullable = false)
    private int time;
}
