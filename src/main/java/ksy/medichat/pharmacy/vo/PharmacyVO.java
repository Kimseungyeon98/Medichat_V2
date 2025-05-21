package ksy.medichat.pharmacy.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PharmacyVO {
	private Long pha_num;				//일련번호
	private String pha_addr;            //주소
	private String pha_etc;             //비고
	private String pha_info;            //기관설명상세
	private String pha_mapImg;          //간이약도
	private String pha_name;            //기관명
	private String pha_tell1;           //대표전화
	private String pha_time1C;          //진료시간(월요일)C
	private String pha_time2C;          //진료시간(화요일)C
	private String pha_time3C;          //진료시간(수요일)C
	private String pha_time4C;          //진료시간(목요일)C
	private String pha_time5C;          //진료시간(금요일)C
	private String pha_time6C;          //진료시간(토요일)C
	private String pha_time7C;          //진료시간(일요일)C
	private String pha_time8C;          //진료시간(공휴일)C
	private String pha_time1S;          //진료시간(월요일)S
	private String pha_time2S;          //진료시간(화요일)S
	private String pha_time3S;          //진료시간(수요일)S
	private String pha_time4S;          //진료시간(목요일)S
	private String pha_time5S;          //진료시간(금요일)S
	private String pha_time6S;          //진료시간(토요일)S
	private String pha_time7S;          //진료시간(일요일)S
	private String pha_time8S;          //진료시간(공휴일)S
	private String pha_hpId;            //기관ID
	private String pha_postCdn1;        //우편번호1
	private String pha_postCdn2;        //우편번호2
	private String pha_lon;             //경도
	private String pha_lat;             //위도
	private String pha_x;               //X좌표
	private String pha_y;               //Y좌표
	private String pha_weekendAt;       //주말진료여부
	
	private int around;					//현재 지점서부터 약국까지의 실제거리
}
