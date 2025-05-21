package ksy.medichat.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatFileDTO {

    private Long chatNum;
    private Long fileNum;
    private String fileName;
    private Long memNum;
    private Long docNum;
    private Integer fileType;
    private String fileValidDate;

    private String memName; // JOIN 결과로 생성되는 값
}