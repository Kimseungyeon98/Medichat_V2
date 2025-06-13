package ksy.medichat.hospital.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import ksy.medichat.hospital.domain.Hospital;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalDTO {
    private Long code; // 일련번호
    private String address; // 주소
    private String division; // 병원분류
    private String divisionName; // 병원분류명
    private String emergencyMedicalCode; // 응급의료기관코드
    private String emergencyMedicalCodeName; // 응급의료기관코드명
    private String emergencyRoomOperatingStatus; // 응급실운영여부(1/2)
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
    private String description; // 기관설명상세
    private Double lat; // 위도
    private Double lng; // 경도
    private String weekendAt; // 주말진료여부

    private Long docCnt;         // 병원에 소속한 의사회원 인원수
    private Long revCnt;         // 병원의 리뷰 갯수
    private Double around;          // 현재 지점서부터 병원까지의 실제거리
    private Double revAvg;          // 병원 평점

    // Entity → DTO 변환
    public static HospitalDTO toDTO(Hospital entity) {
        return HospitalDTO.builder().code(entity.getCode()).address(entity.getAddress()).division(entity.getDivision()).divisionName(entity.getDivisionName()).emergencyMedicalCode(entity.getEmergencyMedicalCode()).emergencyMedicalCodeName(entity.getEmergencyMedicalCodeName()).emergencyRoomOperatingStatus(entity.getEmergencyRoomOperatingStatus()).etc(entity.getEtc()).mapImage(entity.getMapImage()).name(entity.getName()).mainPhone(entity.getMainPhone()).emergencyRoomPhone(entity.getEmergencyRoomPhone()).time1C(entity.getTime1C()).time2C(entity.getTime2C()).time3C(entity.getTime3C()).time4C(entity.getTime4C()).time5C(entity.getTime5C()).time6C(entity.getTime6C()).time7C(entity.getTime7C()).time8C(entity.getTime8C()).time1S(entity.getTime1S()).time2S(entity.getTime2S()).time3S(entity.getTime3S()).time4S(entity.getTime4S()).time5S(entity.getTime5S()).time6S(entity.getTime6S()).time7S(entity.getTime7S()).time8S(entity.getTime8S()).hpId(entity.getHpId()).postCode1(entity.getPostCode1()).postCode2(entity.getPostCode2()).description(entity.getDescription()).lat(entity.getLat()).lng(entity.getLng()).weekendAt(entity.getWeekendAt()).build();
    }

    // DTO → Entity 변환
    public static Hospital toEntity(HospitalDTO dto) {
        return Hospital.builder().code(dto.getCode()).address(dto.getAddress()).division(dto.getDivision()).divisionName(dto.getDivisionName()).emergencyMedicalCode(dto.getEmergencyMedicalCode()).emergencyMedicalCodeName(dto.getEmergencyMedicalCodeName()).emergencyRoomOperatingStatus(dto.getEmergencyRoomOperatingStatus()).etc(dto.getEtc()).mapImage(dto.getMapImage()).name(dto.getName()).mainPhone(dto.getMainPhone()).emergencyRoomPhone(dto.getEmergencyRoomPhone()).time1C(dto.getTime1C()).time2C(dto.getTime2C()).time3C(dto.getTime3C()).time4C(dto.getTime4C()).time5C(dto.getTime5C()).time6C(dto.getTime6C()).time7C(dto.getTime7C()).time8C(dto.getTime8C()).time1S(dto.getTime1S()).time2S(dto.getTime2S()).time3S(dto.getTime3S()).time4S(dto.getTime4S()).time5S(dto.getTime5S()).time6S(dto.getTime6S()).time7S(dto.getTime7S()).time8S(dto.getTime8S()).hpId(dto.getHpId()).postCode1(dto.getPostCode1()).postCode2(dto.getPostCode2()).description(dto.getDescription()).lat(dto.getLat()).lng(dto.getLng()).weekendAt(dto.getWeekendAt()).build();
    }

}