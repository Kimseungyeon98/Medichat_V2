package ksy.medichat.community.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cboard_re")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creNum;

    private Long cboNum;
    private Long memNum;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String creContent;

    private LocalDateTime creRdate;
    private LocalDateTime creMdate;

    private Long creLevel;
    private Long creRef;
    private Long creReport;

    // 연관 정보는 Entity에 직접 포함 X (DTO에서 확장)
}