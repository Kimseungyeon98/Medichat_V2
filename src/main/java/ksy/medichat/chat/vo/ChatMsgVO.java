package ksy.medichat.chat.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMsgVO {
	private long msg_num;
	private long chat_num;
	private String msg_content;
	private int msg_sender_type;
	
	private byte[] msg_image; //이미지 전송 시
}
