package ksy.medichat.hospital.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalVO {
	private Long hos_num;				//일련번호
	private String hos_addr;            //주소
	private String hos_div;             //병원분류
	private String hos_divName;         //병원분류명
	private String hos_emcls;           //응급의료기관코드
	private String hos_emclsName;       //응급의료기관코드명
	private String hos_eryn;            //응급실운영여부(1/2)
	private String hos_etc;             //비고
	private String hos_mapImg;          //간이약도
	private String hos_name;            //기관명
	private String hos_tell1;           //대표전화
	private String hos_tell3;           //응급실전화
	private String hos_time1C;          //진료시간(월요일)C
	private String hos_time2C;          //진료시간(화요일)C
	private String hos_time3C;          //진료시간(수요일)C
	private String hos_time4C;          //진료시간(목요일)C
	private String hos_time5C;          //진료시간(금요일)C
	private String hos_time6C;          //진료시간(토요일)C
	private String hos_time7C;          //진료시간(일요일)C
	private String hos_time8C;          //진료시간(공휴일)C
	private String hos_time1S;          //진료시간(월요일)S
	private String hos_time2S;          //진료시간(화요일)S
	private String hos_time3S;          //진료시간(수요일)S
	private String hos_time4S;          //진료시간(목요일)S
	private String hos_time5S;          //진료시간(금요일)S
	private String hos_time6S;          //진료시간(토요일)S
	private String hos_time7S;          //진료시간(일요일)S
	private String hos_time8S;          //진료시간(공휴일)S
	private String hos_hpId;            //기관ID
	private String hos_postCdn1;        //우편번호1
	private String hos_postCdn2;        //우편번호2
	private String hos_info;            //기관설명상세
	private String hos_lon;             //경도
	private String hos_lat;             //위도
	private String hos_x;               //X좌표
	private String hos_y;               //Y좌표
	private String hos_weekendAt;       //주말진료여부
	
	private int doc_cnt;				//병원에 소속한 의사회원 인원수
	private int rev_cnt;				//병원의 리뷰 갯수
	private int around;					//현재 지점서부터 병원까지의 실제거리
	
	private double rev_avg; 			//병원 평점
}