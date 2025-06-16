package ksy.medichat.pharmacy.dto;

import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.pharmacy.domain.Pharmacy;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharmacyDTO {

    private Long code;				//일련번호
    private String address;         //주소
    private String etc;             //비고
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
    private Double lat;             //위도
    private Double lng;             //경도
    private String weekendAt;       //주말진료여부

    private Double around;

    // Entity → DTO 변환
    public static PharmacyDTO toDTO(Pharmacy entity) {
        return PharmacyDTO.builder().code(entity.getCode()).address(entity.getAddress()).etc(entity.getEtc()).description(entity.getDescription()).mapImage(entity.getMapImage()).name(entity.getName()).mainPhone(entity.getMainPhone()).time1C(entity.getTime1C()).time2C(entity.getTime2C()).time3C(entity.getTime3C()).time4C(entity.getTime4C()).time5C(entity.getTime5C()).time6C(entity.getTime6C()).time7C(entity.getTime7C()).time8C(entity.getTime8C()).time1S(entity.getTime1S()).time2S(entity.getTime2S()).time3S(entity.getTime3S()).time4S(entity.getTime4S()).time5S(entity.getTime5S()).time6S(entity.getTime6S()).time7S(entity.getTime7S()).time8S(entity.getTime8S()).hpId(entity.getHpId()).postCode1(entity.getPostCode1()).postCode2(entity.getPostCode2()).lat(entity.getLat()).lng(entity.getLng()).weekendAt(entity.getWeekendAt()).build();
    }

    // DTO → Entity 변환
    public static Pharmacy toEntity(PharmacyDTO dto) {
        return Pharmacy.builder().code(dto.getCode()).address(dto.getAddress()).etc(dto.getEtc()).description(dto.getDescription()).mapImage(dto.getMapImage()).name(dto.getName()).mainPhone(dto.getMainPhone()).time1C(dto.getTime1C()).time2C(dto.getTime2C()).time3C(dto.getTime3C()).time4C(dto.getTime4C()).time5C(dto.getTime5C()).time6C(dto.getTime6C()).time7C(dto.getTime7C()).time8C(dto.getTime8C()).time1S(dto.getTime1S()).time2S(dto.getTime2S()).time3S(dto.getTime3S()).time4S(dto.getTime4S()).time5S(dto.getTime5S()).time6S(dto.getTime6S()).time7S(dto.getTime7S()).time8S(dto.getTime8S()).hpId(dto.getHpId()).postCode1(dto.getPostCode1()).postCode2(dto.getPostCode2()).lat(dto.getLat()).lng(dto.getLng()).weekendAt(dto.getWeekendAt()).build();
    }
}
