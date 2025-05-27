package ksy.medichat.hospital.dto;


import ksy.medichat.hospital.domain.Hospital;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
    private Long hosNum;            // 일련번호
    private String hosAddr;         // 주소
    private String hosDiv;          // 병원분류
    private String hosDivName;      // 병원분류명
    private String hosEmcls;        // 응급의료기관코드
    private String hosEmclsName;    // 응급의료기관코드명
    private String hosEryn;         // 응급실운영여부(1/2)
    private String hosEtc;          // 비고
    private String hosMapImg;       // 간이약도
    private String hosName;         // 기관명
    private String hosTell1;        // 대표전화
    private String hosTell3;        // 응급실전화

    private String hosTime1C;       // 진료시간(월요일)C
    private String hosTime2C;
    private String hosTime3C;
    private String hosTime4C;
    private String hosTime5C;
    private String hosTime6C;
    private String hosTime7C;
    private String hosTime8C;

    private String hosTime1S;       // 진료시간(월요일)S
    private String hosTime2S;
    private String hosTime3S;
    private String hosTime4S;
    private String hosTime5S;
    private String hosTime6S;
    private String hosTime7S;
    private String hosTime8S;

    private String hosHpId;         // 기관ID
    private String hosPostCdn1;     // 우편번호1
    private String hosPostCdn2;     // 우편번호2
    private String hosInfo;         // 기관설명상세
    private String hosLon;          // 경도
    private String hosLat;          // 위도
    private String hosX;            // X좌표
    private String hosY;            // Y좌표
    private String hosWeekendAt;    // 주말진료여부

    private int docCnt;             // 병원에 소속한 의사회원 인원수
    private int revCnt;             // 병원의 리뷰 갯수
    private int around;             // 현재 지점서부터 병원까지의 실제거리
    private double revAvg;          // 병원 평점

    // Entity → DTO 변환
    public static HospitalDTO toDTO(Hospital entity) {
        return HospitalDTO.builder().hosNum(entity.getHosNum()).hosAddr(entity.getHosAddr()).hosDiv(entity.getHosDiv()).hosDivName(entity.getHosDivName()).hosEmcls(entity.getHosEmcls()).hosEmclsName(entity.getHosEmclsName()).hosEryn(entity.getHosEryn()).hosEtc(entity.getHosEtc()).hosMapImg(entity.getHosMapImg()).hosName(entity.getHosName()).hosTell1(entity.getHosTell1()).hosTell3(entity.getHosTell3()).hosTime1C(entity.getHosTime1C()).hosTime2C(entity.getHosTime2C()).hosTime3C(entity.getHosTime3C()).hosTime4C(entity.getHosTime4C()).hosTime5C(entity.getHosTime5C()).hosTime6C(entity.getHosTime6C()).hosTime7C(entity.getHosTime7C()).hosTime8C(entity.getHosTime8C()).hosTime1S(entity.getHosTime1S()).hosTime2S(entity.getHosTime2S()).hosTime3S(entity.getHosTime3S()).hosTime4S(entity.getHosTime4S()).hosTime5S(entity.getHosTime5S()).hosTime6S(entity.getHosTime6S()).hosTime7S(entity.getHosTime7S()).hosTime8S(entity.getHosTime8S()).hosHpId(entity.getHosHpId()).hosPostCdn1(entity.getHosPostCdn1()).hosPostCdn2(entity.getHosPostCdn2()).hosInfo(entity.getHosInfo()).hosLon(entity.getHosLon()).hosLat(entity.getHosLat()).hosX(entity.getHosX()).hosY(entity.getHosY()).hosWeekendAt(entity.getHosWeekendAt()).build();
    }

    // DTO → Entity 변환
    public static Hospital toEntity(HospitalDTO dto) {
        return Hospital.builder().hosNum(dto.getHosNum()).hosAddr(dto.getHosAddr()).hosDiv(dto.getHosDiv()).hosDivName(dto.getHosDivName()).hosEmcls(dto.getHosEmcls()).hosEmclsName(dto.getHosEmclsName()).hosEryn(dto.getHosEryn()).hosEtc(dto.getHosEtc()).hosMapImg(dto.getHosMapImg()).hosName(dto.getHosName()).hosTell1(dto.getHosTell1()).hosTell3(dto.getHosTell3()).hosTime1C(dto.getHosTime1C()).hosTime2C(dto.getHosTime2C()).hosTime3C(dto.getHosTime3C()).hosTime4C(dto.getHosTime4C()).hosTime5C(dto.getHosTime5C()).hosTime6C(dto.getHosTime6C()).hosTime7C(dto.getHosTime7C()).hosTime8C(dto.getHosTime8C()).hosTime1S(dto.getHosTime1S()).hosTime2S(dto.getHosTime2S()).hosTime3S(dto.getHosTime3S()).hosTime4S(dto.getHosTime4S()).hosTime5S(dto.getHosTime5S()).hosTime6S(dto.getHosTime6S()).hosTime7S(dto.getHosTime7S()).hosTime8S(dto.getHosTime8S()).hosHpId(dto.getHosHpId()).hosPostCdn1(dto.getHosPostCdn1()).hosPostCdn2(dto.getHosPostCdn2()).hosInfo(dto.getHosInfo()).hosLon(dto.getHosLon()).hosLat(dto.getHosLat()).hosX(dto.getHosX()).hosY(dto.getHosY()).hosWeekendAt(dto.getHosWeekendAt()).build();
    }
}