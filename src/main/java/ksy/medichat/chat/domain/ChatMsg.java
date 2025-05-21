package ksy.medichat.chat.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_msg")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_num")
    private Long msgNum;

    @Column(name = "chat_num", nullable = false)
    private Long chatNum;

    @Column(name = "msg_content", nullable = false)
    private String msgContent;

    @Column(name = "msg_sender_type", nullable = false)
    private Integer msgSenderType;

    @Lob
    @Column(name = "msg_image")
    private byte[] msgImage;

}