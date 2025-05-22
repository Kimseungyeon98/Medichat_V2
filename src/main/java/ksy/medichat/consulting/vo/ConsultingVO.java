package ksy.medichat.consulting.vo;

import ksy.medichat.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ConsultingVO {
	private Long con_num;
	private Long mem_num;
	private int con_type;
	private String con_type_name;
	private String con_title;
	private String con_content;
	private String con_rDate;
	private String con_mDate;
	
	private Member member;
	private int con_re_cnt;
}
