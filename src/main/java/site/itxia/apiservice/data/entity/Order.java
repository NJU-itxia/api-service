package site.itxia.apiservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.itxia.apiservice.enumable.Campus;
import site.itxia.apiservice.enumable.OrderStatus;
import site.itxia.apiservice.enumable.OrderWarranty;

import javax.persistence.*;

/**
 * @author zhenxi
 * 预约单实体.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(nullable = false, columnDefinition = "bit(2) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private OrderWarranty warranty;

    @Column(nullable = false, columnDefinition = "bit(2)")
    @Enumerated(value = EnumType.ORDINAL)
    private Campus campus;

    @Column(name = "`description`", columnDefinition = "text")
    private String description;

    @Column(name = "`status`", nullable = false, columnDefinition = "bit(3) default 0")
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(name = "`time`", nullable = false, columnDefinition = "int(32)")
    private int time;

}
