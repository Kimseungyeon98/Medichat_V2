package ksy.medichat.hospital.dto;


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
}