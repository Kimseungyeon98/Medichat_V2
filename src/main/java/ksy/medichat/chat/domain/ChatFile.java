package ksy.medichat.chat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "chat_files") // 실제 테이블명과 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatFile {

    @Id
    @Column(name = "file_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PostgreSQL의 serial, identity 컬럼 사용 가정
    private Long fileNum;

    @Column(name = "chat_num", nullable = false)
    private Long chatNum;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "mem_num", nullable = false)
    private Long memNum;

    @Column(name = "doc_num", nullable = false)
    private Long docNum;

    @Column(name = "file_type", nullable = false)
    private Integer fileType;

    @Column(name = "file_reg_date", nullable = false)
    private Date fileRegDate;

    @Column(name = "file_valid_date", nullable = false)
    private String fileValidDate;

    // JOIN으로 추가된 필드 (DB 컬럼이 아닌 경우)
    @Transient
    private String memName;
}
