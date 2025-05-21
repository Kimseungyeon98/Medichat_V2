package ksy.medichat.reservation.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.chat.service.ChatService;
import ksy.medichat.chat.vo.ChatVO;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.notification.service.NotificationService;
import ksy.medichat.notification.vo.NotificationVO;
import ksy.medichat.reservation.service.ReservationService;
import ksy.medichat.reservation.vo.ReservationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ReservationAjaxController {
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ChatService chatService;
	@Autowired
    private NotificationService notificationService;
	
	@GetMapping("/reservation/reservation")
	@ResponseBody
	public Map<String, String> reservation(Long hos_num, Model model, HttpSession session) {
        log.debug("<<ajax 컨트롤러 진입>>");
        Map<String, String> map = new HashMap<>();
        Object user = session.getAttribute("user");

        if (user == null) {
            map.put("result", "logout");
        } else if (user instanceof DoctorVO) {
            map.put("result", "doctor");
        } else if (user instanceof MemberVO) {
            MemberVO member = (MemberVO) user;
            if (member.getMem_auth() == 1) {
                map.put("result", "suspended");
            } else if (member.getMem_auth() == 2) {
                map.put("result", "success");
                model.addAttribute("hos_num", hos_num);
            } else {
                map.put("result", "unauthorized");
            }
        } else {
            map.put("result", "unknown");
        }

        return map;
    }

	@GetMapping("/reservation/hosHours")
	@ResponseBody
	public Map<String,Object> getHosHours(Long hos_num, HttpSession session) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		if (user == null) {
			map.put("result", "logout");
		}else {
			HospitalVO hospitalVO = reservationService.getHosHours(hos_num);
			//이미 그 시간에 예약이 존재하는지
			map.put("result", "success");
			map.put("hospitalVO", hospitalVO);
		}

		return map;
	}
	
	@GetMapping("/reservation/availableDoctor")
    @ResponseBody
    public Map<String, Object> getAvailableDoctors(@RequestParam Long hos_num, 
                                                   @RequestParam String date,
                                                   @RequestParam String time, 
                                                   @RequestParam int dayOfWeek, 
                                                   HttpSession session) {
        MemberVO user = (MemberVO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("result", "logout");
        } else {
        	log.debug("Received hos_num: " + hos_num);
        	log.debug("Received date: " + date);
        	log.debug("Received time: " + time);
        	log.debug("Received dayOfWeek: " + dayOfWeek);
            Map<String, Object> params = new HashMap<>();
            params.put("hos_num", hos_num);
            params.put("date", date);
            params.put("time", time);
            params.put("dayOfWeek", dayOfWeek);
            List<DoctorVO> doctors = reservationService.getAvailableDoctors(params);
            map.put("result", "success");
            map.put("doctors", doctors);
            log.debug("Doctors: " + doctors); // 추가한 로그
        }
        return map;
    }
	
	@PostMapping("/reservation/reservationcompleted")
	@ResponseBody
	public Map<String, Object> submitReservation(@RequestBody Map<String, Object> reservationData, HttpSession session) {
	    Map<String, Object> map = new HashMap<>();
	    MemberVO user = (MemberVO) session.getAttribute("user");

	    if (user == null) {
	        map.put("result", "logout");
	    } else {
	        // 로그 추가
	        System.out.println("Received Reservation Data: " + reservationData);

	        ReservationVO reservationVO = new ReservationVO();
	        reservationVO.setMem_num(user.getMem_num());
	        reservationVO.setDoc_num(Long.parseLong(reservationData.get("doc_num").toString()));
	        reservationVO.setRes_type(Long.parseLong(reservationData.get("res_type").toString()));
	        reservationVO.setRes_date(reservationData.get("res_date").toString());
	        reservationVO.setRes_time(reservationData.get("res_time").toString());
	        reservationVO.setRes_content(reservationData.get("res_content").toString());

	        // 로그 추가
	        System.out.println("Mapped Reservation Data: " + reservationVO);

	        try {
	            reservationService.insertReservation(reservationVO);
	            // 회원에게 알림 보내기
	            NotificationVO memberNotification = new NotificationVO();
	            memberNotification.setMem_num(user.getMem_num());
	            memberNotification.setNoti_category(1L); // 진료 관련
	            memberNotification.setNoti_message(reservationVO.getRes_date() + " " + reservationVO.getRes_time()+" 예약 대기 중입니다.");
	            memberNotification.setNoti_link("<a href='/reservation/myResList'>나의 예약 내역<a>");
	            memberNotification.setNoti_priority(1);

	            notificationService.insertNotification(memberNotification);
	            
	            // 의사에게 알림 보내기
	            NotificationVO doctorNotification = new NotificationVO();
	            doctorNotification.setMem_num(reservationVO.getDoc_num());
	            doctorNotification.setNoti_category(1L); // 진료 관련
	            doctorNotification.setNoti_message("새로운 예약 신청이 있습니다.");
	            doctorNotification.setNoti_link("<a href='/reservation/docResList'>나의 예약 내역<a>"); // 링크를 직접 설정
	            doctorNotification.setNoti_priority(1);

	            notificationService.insertNotification(doctorNotification);

	            
	            map.put("result", "success");
	        } catch (Exception e) {
	            e.printStackTrace();
	            map.put("result", "error");
	        }
	    }

	    return map;
	}

	@PostMapping("/reservation/cancelReservation")
	@ResponseBody
	public Map<String,Object> cancelReservation(long res_num,HttpSession session,Model model) {
		Map<String, Object> map = new HashMap<>();
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {
			reservationService.cancelReservation(res_num);
			// 회원에게 알림 보내기
            NotificationVO memberNotification = new NotificationVO();
            memberNotification.setMem_num(user.getMem_num());
            memberNotification.setNoti_category(1L); // 진료 관련
            memberNotification.setNoti_message(" 예약이 취소되었습니다.");
            memberNotification.setNoti_link("<a href='/reservation/myResList'>나의 예약 내역<a>");
            memberNotification.setNoti_priority(1);

            notificationService.insertNotification(memberNotification);
            
            // 의사에게 알림 보내기
            long doc_num = reservationService.selectDoc_num(res_num);
            NotificationVO doctorNotification = new NotificationVO();
            doctorNotification.setMem_num(doc_num);
            doctorNotification.setNoti_category(1L); // 진료 관련
            doctorNotification.setNoti_message("취소된 예약내역이 있습니다.");
            doctorNotification.setNoti_link("<a href='/reservation/docResList'>나의 예약 내역<a>");
            doctorNotification.setNoti_priority(1);

            notificationService.insertNotification(doctorNotification);
			map.put("result", "success");
		}
		return map;
	}
	
	@PostMapping("/reservation/updateReservation")
	@ResponseBody
	public Map<String,Object> updateReservation(long res_num,long res_status,HttpSession session,Model model){
		Map<String,Object> map = new HashMap<>();
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if(user == null) {
			map.put("result", "logout");
		}else {

			long res_type = chatService.selectResType(res_num);
			long mem_num = chatService.selectMem_num(res_num);
			
			if(res_type == 0){
				ChatVO chatVO = new ChatVO();

				chatVO.setMem_num(mem_num);
				chatVO.setDoc_num(user.getMem_num());
				chatVO.setRes_num(res_num);

				chatService.createChat(chatVO);
			}
			reservationService.updateReservation(res_num,res_status);
			// 회원에게 알림 보내기
            NotificationVO memberNotification = new NotificationVO();
            //ReservationVO reservationVO = reservationService.getReservationById(res_num);
            memberNotification.setMem_num(mem_num);
            memberNotification.setNoti_category(1L); // 진료 관련
            memberNotification.setNoti_message("예약정보가 업데이트 되었습니다.");
            memberNotification.setNoti_link("<a href='/reservation/myResList'>나의 예약 내역<a>");
            memberNotification.setNoti_priority(1);

            notificationService.insertNotification(memberNotification);
            
            // 의사에게 알림 보내기
            NotificationVO doctorNotification = new NotificationVO();
            doctorNotification.setMem_num(user.getMem_num());
            doctorNotification.setNoti_category(1L); // 진료 관련
            doctorNotification.setNoti_message("예약 정보가 업데이트 되었습니다.");
            doctorNotification.setNoti_link("<a href='/reservation/docResList'>나의 예약 내역<a>"); // 링크를 직접 설정

            notificationService.insertNotification(doctorNotification);
			
			map.put("result", "success");
		}
		return map;
	}
}
	

