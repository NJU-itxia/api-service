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
@Table(name = "upload")
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "upload_by", nullable = false)
    private int uploadBy;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String sha256sum;

    @Column(name = "`time`", nullable = false)
    private int time;

    @Column(name = "`delete`", nullable = false)
    private boolean delete;
}
