package ksy.medichat.notification.service;

import ksy.medichat.notification.dao.NotificationMapper;
import ksy.medichat.notification.vo.NotificationVO;
import ksy.medichat.util.DurationFromNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationMapper notificationMapper;
	
	@Override
	public void insertNotification(NotificationVO notificationVO) {
		notificationMapper.insertNotification(notificationVO);		
	}

	@Override
	public void readNotification(Long noti_num) {
		notificationMapper.readNotification(noti_num);
	}

	@Override
	public NotificationVO selectNotification(Long noti_num) {
		return notificationMapper.selectNotification(noti_num);
	}

	@Override
	public int selectCountNotification(Long mem_num) {
		return notificationMapper.selectCountNotification(mem_num);
	}

	@Override
	public List<NotificationVO> selectListNotification(Long mem_num) {
		List<NotificationVO> nList = notificationMapper.selectListNotification(mem_num);
		for(NotificationVO noti : nList) {
			noti.setNoti_createdDate(DurationFromNow.getTimeDiffLabel(noti.getNoti_createdDate()));
		}
		
		return nList;
	}

	@Override
	public void deleteNotification(Long noti_num) {
		notificationMapper.deleteNotification(noti_num);
	}
	
}
