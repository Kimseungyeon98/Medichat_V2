package ksy.medichat.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class ChatFileVO {
	private long chat_num;
	private long file_num;
	private String file_name;
	private long mem_num;
	private long doc_num;
	private int file_type;
	private Date file_reg_date;
	private String file_valid_date;
	
	//JOIN으로 생성
	private String mem_name;
}
