package ksy.medichat.chat.service;

import ksy.medichat.chat.dao.ChatMapper;
import ksy.medichat.chat.vo.ChatFileVO;
import ksy.medichat.chat.vo.ChatMsgVO;
import ksy.medichat.chat.vo.ChatPaymentVO;
import ksy.medichat.chat.vo.ChatVO;
import ksy.medichat.reservation.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatMapper chatMapper;

	@Override
	public List<ChatVO> selectChatListForMem(long mem_num) {
		return chatMapper.selectChatListForMem(mem_num);
	}

	@Override
	public List<ChatVO> selectChatListForDoc(long mem_num) {
		return chatMapper.selectChatListForDoc(mem_num);
	}
	
	@Override
	public ReservationVO selectReservationByChatNum(long chat_num) {
		return chatMapper.selectReservationByChatNum(chat_num);
	}

	@Override
	public long selectMem_num(long res_num) {
		return chatMapper.selectMem_num(res_num);
	}

	@Override
	public void createChat(ChatVO chatVO) {
		chatMapper.createChat(chatVO);
	}
	
	@Override
	public ChatVO selectChat(long chat_num) {
		return chatMapper.selectChat(chat_num);
	}
	
	@Override
	public long selectResType(long res_num) {
		return chatMapper.selectResType(res_num);
	}

	@Override
	public List<ChatMsgVO> selectMsg(long chat_num) {
		return chatMapper.selectMsg(chat_num);
	}
	
	@Override
	public void insertMsg(ChatMsgVO chatMsgVO) {
		chatMapper.insertMsg(chatMsgVO);
	}
	
	@Override
	public void insertImage(ChatMsgVO chatMsgVO) {
		chatMapper.insertImage(chatMsgVO);
	}
	
	@Override
	public void insertChatFile(ChatFileVO chatFileVO) {
		chatMapper.insertChatFile(chatFileVO);
	}
	
	@Override
	public ChatFileVO selectFile(long file_num) {
		return chatMapper.selectFile(file_num);
	}
	
	@Override
	public long selectFileNum(long chat_num, String file_name) {
		return chatMapper.selectFileNum(chat_num, file_name);
	}
	
	@Override
	public void deleteFile(long file_num) {
		chatMapper.deleteFile(file_num);
	}
	
	@Override
	public void insertChatPayment(ChatPaymentVO chatPaymentVO) {
		chatMapper.insertChatPayment(chatPaymentVO);
	}

	@Override
	public List<ChatFileVO> selectFiles(long mem_num) {
		return chatMapper.selectFiles(mem_num);
	}
	
	@Override
	public void updateChatStatus(long chat_num) {
		chatMapper.updateChatStatus(chat_num);
	}
}
