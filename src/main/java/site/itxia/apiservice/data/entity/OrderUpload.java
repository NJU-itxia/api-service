package site.itxia.apiservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhenxi
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class OrderUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id", nullable = false)
    private int orderID;

    @Column(name = "upload_id", nullable = false)
    private int uploadID;

    @Column(name = "`delete`", nullable = false)
    private boolean delete;
}
