package ksy.medichat.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatPaymentDTO {
    private Long payNum;
    private Long chatNum;
    private Long memNum;
    private String docName;
    private Integer payAmount;

    private String memPhone;
}