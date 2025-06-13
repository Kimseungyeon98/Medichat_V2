package ksy.medichat.hospital.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hospital {

    @Id
    private Long code; // 일련번호
    private String address; // 주소
    private String division; // 병원분류
    private String divisionName; // 병원분류명
    private String emergencyMedicalCode; // 응급의료기관코드
    private String emergencyMedicalCodeName; // 응급의료기관코드명
    private String emergencyRoomOperatingStatus; // 응급실운영여부(1/2)
    @Column(length = 1500)
    private String etc; // 비고
    private String mapImage; // 간이약도
    private String name; // 기관명
    private String mainPhone; // 대표전화
    private String emergencyRoomPhone; // 응급실전화
    private String time1C; // 진료시간(월요일)C
    private String time2C;
    private String time3C;
    private String time4C;
    private String time5C;
    private String time6C;
    private String time7C;
    private String time8C;
    private String time1S; // 진료시간(월요일)S
    private String time2S;
    private String time3S;
    private String time4S;
    private String time5S;
    private String time6S;
    private String time7S;
    private String time8S;
    private String hpId; // 기관ID
    private String postCode1; // 우편번호1
    private String postCode2; // 우편번호2
    @Column(length = 500)
    private String description; // 기관설명상세
    private Double lat; // 위도
    private Double lng; // 경도
    private String weekendAt; // 주말진료여부

}
