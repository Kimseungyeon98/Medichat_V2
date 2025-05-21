package ksy.medichat.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDTO {
    private Long chatNum;
    private Long memNum;
    private Long docNum;
    private LocalDateTime chatRegDate;
    private Long chatStatus;
    private Long resNum;

    private String memName;
    private String resDate;
    private String resTime;
}