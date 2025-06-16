package ksy.medichat.pharmacy.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pharmacy {

    @Id
    private Long code;				//일련번호
    private String address;         //주소
    @Column(length = 1500)
    private String etc;             //비고
    @Column(length = 500)
    private String description;     //기관설명상세
    private String mapImage;        //간이약도
    private String name;            //기관명
    private String mainPhone;       //대표전화
    private String time1C;          //진료시간(월요일)C
    private String time2C;          //진료시간(화요일)C
    private String time3C;          //진료시간(수요일)C
    private String time4C;          //진료시간(목요일)C
    private String time5C;          //진료시간(금요일)C
    private String time6C;          //진료시간(토요일)C
    private String time7C;          //진료시간(일요일)C
    private String time8C;          //진료시간(공휴일)C
    private String time1S;          //진료시간(월요일)S
    private String time2S;          //진료시간(화요일)S
    private String time3S;          //진료시간(수요일)S
    private String time4S;          //진료시간(목요일)S
    private String time5S;          //진료시간(금요일)S
    private String time6S;          //진료시간(토요일)S
    private String time7S;          //진료시간(일요일)S
    private String time8S;          //진료시간(공휴일)S
    private String hpId;            //기관ID
    private String postCode1;       //우편번호1
    private String postCode2;       //우편번호2
    private String lat;             //위도
    private String lng;             //경도
    private String weekendAt;       //주말진료여부

}
