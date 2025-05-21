package ksy.medichat.chat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_num")
    private Long chatNum;

    @Column(name = "mem_num", nullable = false)
    private Long memNum;

    @Column(name = "doc_num", nullable = false)
    private Long docNum;

    @Column(name = "chat_reg_date", nullable = false)
    private LocalDateTime chatRegDate;

    @Column(name = "chat_status", nullable = false)
    private Long chatStatus;

    @Column(name = "res_num", nullable = false)
    private Long resNum;

    // 조인으로 생성한 컬럼들(DB 컬럼 아님)
    @Transient
    private String memName;

    @Transient
    private String resDate;

    @Transient
    private String resTime;

}