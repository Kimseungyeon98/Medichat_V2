package ksy.medichat.drug.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DrugInfoVO {
	private Long drg_num; 
	private String drg_name;		//의약품명
	private String drg_code; 		//품목기준코드
	private String drg_company; 	//업체명
	private String drg_effect; 		//효능
	private String drg_dosage;		//복용법
	private String drg_warning; 	//경고
	private String drg_precaution; 	//주의사항
	private String drg_interaction; //상호작용
	private String drg_seffect; 	//부작용
	private String drg_storage; 	//보관법
	private String drg_img; 		//낱알이미지
}
