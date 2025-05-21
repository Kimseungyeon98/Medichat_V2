package ksy.medichat.notification.controller;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.notification.service.NotificationService;
import ksy.medichat.notification.vo.NotificationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notification-json")
	@ResponseBody
	public Map<String, Object> getNoti(HttpSession session){
		Map<String,Object> map = new HashMap<String, Object>();

		Object user = session.getAttribute("user");
		Integer noti_cnt = null;
		List<NotificationVO> noti_list = null;
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				MemberVO member = (MemberVO)user;
				noti_cnt = notificationService.selectCountNotification(member.getMem_num());
				noti_list = notificationService.selectListNotification(member.getMem_num());
			} else if (user.getClass().equals(DoctorVO.class)){
				DoctorVO doctor = (DoctorVO)user;
				noti_cnt = notificationService.selectCountNotification(doctor.getMem_num());
				noti_list = notificationService.selectListNotification(doctor.getMem_num());
			}
			map.put("cnt", noti_cnt);
			session.setAttribute("noti_cnt", noti_cnt);
			map.put("list", noti_list);
			session.setAttribute("noti_list", noti_list);
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	@GetMapping("/notificationReaded")
	@ResponseBody
	public Map<String, Object> notiReaded(HttpSession session, @RequestParam Long noti_num){
		Map<String,Object> map = new HashMap<String, Object>();
		
		Object user = session.getAttribute("user");
		Integer noti_cnt = null;
		List<NotificationVO> noti_list = null;
		
		if(user!=null) {
			notificationService.readNotification(noti_num);
			
			if(user.getClass().equals(MemberVO.class)) {
				MemberVO member = (MemberVO)user;
				noti_cnt = notificationService.selectCountNotification(member.getMem_num());
				noti_list = notificationService.selectListNotification(member.getMem_num());
			} else if (user.getClass().equals(DoctorVO.class)){
				DoctorVO doctor = (DoctorVO)user;
				noti_cnt = notificationService.selectCountNotification(doctor.getMem_num());
				noti_list = notificationService.selectListNotification(doctor.getMem_num());
			}
			map.put("cnt", noti_cnt);
			session.setAttribute("noti_cnt", noti_cnt);
			map.put("list", noti_list);
			session.setAttribute("noti_list", noti_list);
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
}
