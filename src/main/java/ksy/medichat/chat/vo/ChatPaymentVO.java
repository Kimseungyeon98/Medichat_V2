package ksy.medichat.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatPaymentVO {
	private long pay_num;
	private long chat_num;
	private long mem_num; //결제인
	private String doc_name; //담당 의사
	private int pay_amount; //결제금액
	
	//api에 전달해야하는 mem_phone 값
	private String mem_phone;
}
