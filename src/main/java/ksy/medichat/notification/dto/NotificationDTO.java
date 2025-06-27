package ksy.medichat.notification.dto;

import ksy.medichat.notification.domain.Notification;
import ksy.medichat.notification.domain.NotificationCategory;
import ksy.medichat.user.domain.User;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDTO {

    private Long code;                          // 알림 식별자
    private NotificationCategory category;      // 알림 카테고리
    private String message;                     // 알림 메시지
    private String link;                        // 관련 링크
    private String type;                        // 알림 타입
    private Integer priority;                   // 우선순위
    private Date sentDate;                      // 발송 일시
    private Date readDate;                      // 읽은 일시
    private Boolean isRead = false;             // 읽음 여부
    private Long userCode;                       // 받는 회원

    // Entity → DTO 변환
    public static NotificationDTO toDTO(Notification entity) {
        return NotificationDTO.builder().code(entity.getCode()).category(entity.getCategory()).message(entity.getMessage()).link(entity.getLink()).type(entity.getType()).priority(entity.getPriority()).sentDate(entity.getSentDate()).readDate(entity.getReadDate()).isRead(entity.getIsRead()).userCode(entity.getUser().getCode()).build();
    }

    // DTO → Entity 변환
    public static Notification toEntity(NotificationDTO dto) {
        return Notification.builder().code(dto.getCode()).category(dto.getCategory()).message(dto.getMessage()).link(dto.getLink()).type(dto.getType()).priority(dto.getPriority()).sentDate(dto.getSentDate()).readDate(dto.getReadDate()).isRead(dto.getIsRead()).user(User.builder().code(dto.getUserCode()).build()).build();
    }

}