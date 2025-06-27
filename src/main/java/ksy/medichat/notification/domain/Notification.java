package ksy.medichat.notification.domain;

import jakarta.persistence.*;
import ksy.medichat.user.domain.User;
import lombok.*;

import java.sql.Date;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationCategory category;

    @Column(length = 2000)
    private String message;

    @Column(length = 2000)
    private String link;

    private String type;

    private Integer priority;

    private Date sentDate;

    private Date readDate;

    private Boolean isRead = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userCode")
    private User user;

}
