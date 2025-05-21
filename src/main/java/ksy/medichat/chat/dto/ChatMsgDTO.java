package ksy.medichat.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMsgDTO {

    private Long msgNum;
    private Long chatNum;
    private String msgContent;
    private Integer msgSenderType;
    private byte[] msgImage;
}