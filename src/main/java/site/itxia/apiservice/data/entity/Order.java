package site.itxia.apiservice.data.entity;

import lombok.Data;
import site.itxia.apiservice.enumable.OrderStatus;

import javax.persistence.*;

/**
 * @author zhenxi
 * 预约单实体.
 */
@Data
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_qq")
    private String customerQQ;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(nullable = false, columnDefinition = "bit(2) default 0")
    private int warranty;

    @Column(nullable = false, columnDefinition = "bit(2)")
    private int location;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false, columnDefinition = "bit(3) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(name = "`time`", nullable = false, columnDefinition = "int(32)")
    private int time;

}