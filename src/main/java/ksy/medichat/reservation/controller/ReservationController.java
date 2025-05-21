package ksy.medichat.reservation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.reservation.service.ReservationService;
import ksy.medichat.reservation.vo.ReservationVO;
import ksy.medichat.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@ModelAttribute
	public ReservationVO initCommand() {
		return new ReservationVO();
	}
	
	@GetMapping("/reservation/myResList")
	public String getMyResList (@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") String keyfield, HttpServletRequest request,HttpSession session,Model model) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/member/login");
			model.addAttribute("alertType","warning");
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mem_num", user.getMem_num());
			int count = reservationService.selectCountByMem(user.getMem_num());
			map.put("keyfield", keyfield);
			PagingUtil page = new PagingUtil(keyfield,null,pageNum,count,2,10,"myResList");
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			List<ReservationVO> list = reservationService.getMyResList(map);
			
			model.addAttribute("count",count);
			model.addAttribute("list",list);
			model.addAttribute("page",page.getPage());
			
			return "myReservationList";
		}
		return "common/resultAlert";
	}
	
	@GetMapping("/reservation/docResList")
	public String getDocResList (@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") String keyfield, HttpServletRequest request,HttpSession session,Model model) {
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/doctor/login");
			model.addAttribute("alertType","warning");
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("doc_num",user.getMem_num());
			int count = reservationService.selectCountByDoc(map);
			map.put("keyfield", keyfield);
			PagingUtil page = new PagingUtil(keyfield,null,pageNum,count,3,10,"docResList");
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			List<ReservationVO> list = reservationService.getDocResList(map);
			
			model.addAttribute("count",count);
			model.addAttribute("list",list);
			model.addAttribute("page",page.getPage());
			
			return "docReservationList";
		}
		return "common/resultAlert";
	}
	
	@GetMapping("/reservation/docCompletedList")
	public String docCompletedList (@RequestParam(defaultValue="1") int pageNum,@RequestParam(defaultValue="1") String keyfield, HttpServletRequest request,HttpSession session,Model model) {
		DoctorVO user = (DoctorVO) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 이용해주세요");
			model.addAttribute("url",request.getContextPath()+"/doctor/login");
			model.addAttribute("alertType","warning");
		}else {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("doc_num",user.getMem_num());
			int count = reservationService.selectCountByCompleted(map);
			map.put("keyfield", keyfield);
			PagingUtil page = new PagingUtil(keyfield,null,pageNum,count,3,10,"docCompletedList");
			map.put("start",page.getStartRow());
			map.put("end", page.getEndRow());
			List<ReservationVO> list = reservationService.getDocCompletedList(map);
			
			model.addAttribute("count",count);
			model.addAttribute("list",list);
			model.addAttribute("page",page.getPage());
			
			return "docCompletedList";
		}
		return "common/resultAlert";
	}
	
	 @GetMapping("/reservation/myPageCalendar")
	 @ResponseBody
	    public Map<String, Object> getReservationsByMember(HttpSession session, HttpServletRequest request) {
	        Map<String, Object> map = new HashMap<>();
	        MemberVO user = (MemberVO) session.getAttribute("user");
	        if (user == null) {
	            map.put("result","logout");
	        } else {
	            List<String> reservations = reservationService.getReservationsByMember(user.getMem_num());
	            map.put("result", "success");
	            map.put("reservations", reservations);
	        }
	        return map;
	    }
		
    }
