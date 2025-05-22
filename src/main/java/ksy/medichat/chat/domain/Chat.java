package ksy.medichat.chat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Builder
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

}