package ksy.medichat.drug.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDrugVO {
	private long med_num;
	private long mem_num;
	private String med_title;
	private String med_name;
	private String med_sdate;
	private String med_edate;
	private String med_time;
	private String med_dosage;
	private String med_note;
}
