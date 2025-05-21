package ksy.medichat.community.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cboard")  // 여기 테이블명 맞춤
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cbo_num")
    private Long cboNum;

    @Column(name = "mem_num", nullable = false)
    private Long memNum;

    @Column(name = "cbo_type", nullable = false)
    private Long cboType;

    @Column(name = "cbo_title", nullable = false)
    private String cboTitle;

    @Column(name = "cbo_content", nullable = false, columnDefinition = "TEXT")
    private String cboContent;

    @Column(name = "cbo_hit")
    private int cboHit;

    @Column(name = "cbo_rdate")
    private LocalDateTime cboRdate;

    @Column(name = "cbo_mdate")
    private LocalDateTime cboMdate;

    @Column(name = "cbo_report")
    private int cboReport;

    // JOIN 결과용, DB 컬럼 아님
    @Transient
    private String memId;

    @Transient
    private int reCnt;

    @Transient
    private int favCnt;

}
