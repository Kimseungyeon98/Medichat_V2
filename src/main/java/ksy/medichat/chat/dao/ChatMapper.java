package ksy.medichat.chat.dao;

import ksy.medichat.chat.vo.ChatFileVO;
import ksy.medichat.chat.vo.ChatMsgVO;
import ksy.medichat.chat.vo.ChatPaymentVO;
import ksy.medichat.chat.vo.ChatVO;
import ksy.medichat.reservation.vo.ReservationVO;

import java.util.List;


public interface ChatMapper {
	//해당 회원 채팅방 목록 불러오기 - 일반회원
	public List<ChatVO> selectChatListForMem(long mem_num);
	
	//해당 회원 채팅방 목록 불러오기 - 의사회원
	public List<ChatVO> selectChatListForDoc(long mem_num);
	
	//예약 번호를 기반으로 멤버 번호 가져오기
	//("SELECT mem_num FROM reservation WHERE res_num=#{res_num}")
	public long selectMem_num(long res_num);
	
	//비대면 예약 구분하기
	//("SELECT res_type FROM reservation WHERE res_num=#{res_num}")
	public long selectResType(long res_num);
	
	//채팅방 만들기
	public void createChat(ChatVO chatVO);
	
	//채팅방 정보 구하기
	//("SELECT * FROM chat WHERE chat_num=#{chat_num}")
	public ChatVO selectChat(long chat_num);
	
	//예약날짜 및 시간 불러오기
	//("SELECT res_date, res_time FROM chat JOIN reservation USING(res_num) WHERE chat_num=#{chat_num}")
	public ReservationVO selectReservationByChatNum(long chat_num);
	
	//채팅방 메시지 불러오기
	//("SELECT * FROM chat_msg	WHERE chat_num=#{chat_num} ORDER BY msg_num ASC")
	public List<ChatMsgVO> selectMsg(long chat_num);
	
	//채팅방 메시지 입력
	//("INSERT INTO chat_msg(msg_num, chat_num, msg_content, msg_sender_type) VALUES (msg_seq.nextval, #{chat_num}, #{msg_content}, #{msg_sender_type})")
	public void insertMsg(ChatMsgVO chatMsgVO);
	
	//채팅방 이미지 전송
	public void insertImage(ChatMsgVO chatMsgVO);
	
	//진료 종료 시 파일 전송
	public void insertChatFile(ChatFileVO chatFileVO);
	
	//파일 다운로드를 위한 파일 정보 불러오기
	//("SELECT * FROM chat_files WHERE file_num=#{file_num}")
	public ChatFileVO selectFile(long file_num);
	
	//파일 번호 불러오기
	//("SELECT file_num FROM chat_files WHERE chat_num=#{chat_num} AND file_name=#{file_name}")
	public Long selectFileNum(long chat_num,String file_name);
	
	//불러온 파일 번호를 토대로 파일 지우기
	//("DELETE FROM chat_files WHERE file_num=#{file_num}")
	public void deleteFile(long file_num);
	
	//결제 정보 입력
	//("INSERT INTO chat_payment(pay_num,chat_num,mem_num,pay_amount) VALUES (payment_seq.nextval,#{chat_num},#{mem_num},#{pay_amount})")
	public void insertChatPayment(ChatPaymentVO chatPaymentVO);
	
	//나의 서류함 파일 리스트 불러오기
	public List<ChatFileVO> selectFiles(long mem_num);
	
	//채팅방 비활성화
	//("UPDATE chat SET chat_status=1 WHERE chat_num=#{chat_num}")
	public void updateChatStatus(long chat_num);
	
}
