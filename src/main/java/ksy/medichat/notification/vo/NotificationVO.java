package ksy.medichat.notification.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotificationVO {
	private Long noti_num;				//알림 식별자
	private Long mem_num;				//받는 회원 식별자
	private Long noti_category;			//알림의 카테고리
	private String noti_message;		//알림 내용
	private int noti_isRead;			//알림 읽었는지 여부
	private String noti_readDate;		//알림 읽은 날짜
	private String noti_resDate;		//알림 등록 날짜
	private String noti_createdDate;	//알림 생성 날짜
	private String noti_link;			//알림에 넣어 둘 링크
	private String noti_type;			//알림 타입(현재는 PUSH 고정이지만 유동적 확장 고려)
	private int noti_priority;			//알림 우선순위여부
}