package ksy.medichat.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_num")
    private Long payNum;

    @Column(name = "chat_num", nullable = false)
    private Long chatNum;

    @Column(name = "mem_num", nullable = false)
    private Long memNum;

    @Column(name = "doc_name", nullable = false, length = 100)
    private String docName;

    @Column(name = "pay_amount", nullable = false)
    private Integer payAmount;

    // mem_phone은 DB 컬럼이 아니고 API 전달용이라 @Transient 처리
    @Transient
    private String memPhone;

}