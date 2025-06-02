package ksy.medichat.hospital.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hospital {

    @Id
    @Column(name = "hos_num")
    private Long hosNum; // 일련번호

    @Column(name = "hos_addr")
    private String hosAddr; // 주소

    @Column(name = "hos_div")
    private String hosDiv; // 병원분류

    @Column(name = "hos_divName")
    private String hosDivName; // 병원분류명

    @Column(name = "hos_emcls")
    private String hosEmcls; // 응급의료기관코드

    @Column(name = "hos_emclsName")
    private String hosEmclsName; // 응급의료기관코드명

    @Column(name = "hos_eryn")
    private String hosEryn; // 응급실운영여부(1/2)

    @Column(name = "hos_etc", length = 1500)
    private String hosEtc; // 비고

    @Column(name = "hos_mapImg")
    private String hosMapImg; // 간이약도

    @Column(name = "hos_name")
    private String hosName; // 기관명

    @Column(name = "hos_tell1")
    private String hosTell1; // 대표전화

    @Column(name = "hos_tell3")
    private String hosTell3; // 응급실전화

    @Column(name = "hos_time1C")
    private String hosTime1C; // 진료시간(월요일)C
    @Column(name = "hos_time2C")
    private String hosTime2C;
    @Column(name = "hos_time3C")
    private String hosTime3C;
    @Column(name = "hos_time4C")
    private String hosTime4C;
    @Column(name = "hos_time5C")
    private String hosTime5C;
    @Column(name = "hos_time6C")
    private String hosTime6C;
    @Column(name = "hos_time7C")
    private String hosTime7C;
    @Column(name = "hos_time8C")
    private String hosTime8C;

    @Column(name = "hos_time1S")
    private String hosTime1S; // 진료시간(월요일)S
    @Column(name = "hos_time2S")
    private String hosTime2S;
    @Column(name = "hos_time3S")
    private String hosTime3S;
    @Column(name = "hos_time4S")
    private String hosTime4S;
    @Column(name = "hos_time5S")
    private String hosTime5S;
    @Column(name = "hos_time6S")
    private String hosTime6S;
    @Column(name = "hos_time7S")
    private String hosTime7S;
    @Column(name = "hos_time8S")
    private String hosTime8S;

    @Column(name = "hos_hpId")
    private String hosHpId; // 기관ID

    @Column(name = "hos_postCdn1")
    private String hosPostCdn1; // 우편번호1

    @Column(name = "hos_postCdn2")
    private String hosPostCdn2; // 우편번호2

    @Column(name = "hos_info", length = 500)
    private String hosInfo; // 기관설명상세

    @Column(name = "hos_lon")
    private Double hosLon; // 경도

    @Column(name = "hos_lat")
    private Double hosLat; // 위도

    @Column(name = "hos_weekendAt")
    private String hosWeekendAt; // 주말진료여부

}
