package ksy.medichat.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class ChatVO {
	private long chat_num;
	private long mem_num;
	private long doc_num;
	private Date chat_reg_date;
	private long chat_status;
	private long res_num;		//예약번호
	
	//조인으로 생성
	private String mem_name;	//의사 이름 or 환자 이름
	private String res_date;	//예약날짜
	private String res_time;	//예약시간
	
}
