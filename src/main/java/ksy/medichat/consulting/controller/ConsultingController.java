package ksy.medichat.consulting.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.consulting.service.ConsultingService;
import ksy.medichat.consulting.vo.Con_ReVO;
import ksy.medichat.consulting.vo.ConsultingVO;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.notification.service.NotificationService;
import ksy.medichat.notification.vo.NotificationVO;
import ksy.medichat.util.DurationFromNow;
import ksy.medichat.util.StringUtil;
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
public class ConsultingController {
	
	@Autowired
	private ConsultingService consultingService;
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/getRightNavData")
	@ResponseBody
	public List<ConsultingVO> getListConsulting(){
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum" , 1);
		map.put("pageItemNum", 5);
		map.put("con_type", 0);;
		
		return consultingService.getListConsulting(map);
	}
	
	@GetMapping("/consultings")
	public String consultings(Model model, @RequestParam(defaultValue="1") int pageNum, @RequestParam(defaultValue="5") int pageItemNum, @RequestParam(defaultValue="0") int con_type) {
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum", pageNum);
		map.put("pageItemNum", pageItemNum);
		map.put("con_type", con_type);		
		
		List<ConsultingVO> consultingList = consultingService.getListConsulting(map);
		model.addAttribute("consultingList", consultingList);
		model.addAttribute("con_type",con_type);
		return "consultings";
	}
	@GetMapping("/consultings-ajax")
	@ResponseBody
	public List<ConsultingVO> consultings(@RequestParam(defaultValue="1") int pageNum, @RequestParam(defaultValue="5") int pageItemNum, @RequestParam(defaultValue="0") int con_type) {
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum", pageNum);
		map.put("pageItemNum", pageItemNum);
		map.put("con_type", con_type);		
		
		List<ConsultingVO> consultingList = consultingService.getListConsulting(map);
		
		return consultingList;
	}
	
	@GetMapping("/consultings/create")
	public String createForm(HttpSession session, Model model) {
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				return "cWrite";
			} else if(user.getClass().equals(DoctorVO.class)) {
				model.addAttribute("url", "/main/main");
				model.addAttribute("message","의사 회원은 글을 작성하실 수 없습니다.");
				model.addAttribute("alertType","warning");
				return "/common/resultAlert";
			} else {
				return "404";
			}
		} else {
			model.addAttribute("url", "/member/login");
			model.addAttribute("message", "회원만 이용 가능한 서비스 입니다.");
			model.addAttribute("alertType","warning");
			return "/common/resultAlert";
		}
	}
	
	@PostMapping("/consultings/create")
	public String create(ConsultingVO consulting, Model model, HttpSession session) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		consulting.setMem_num(user.getMem_num());
		
		log.debug("<<상담 글 등록>> : " + consulting);
		
		consulting.setCon_content(StringUtil.useBrHtml(consulting.getCon_content()));
		
		consultingService.createConsulting(consulting);
		
		model.addAttribute("message","글이 성공적으로 등록되었습니다");
		model.addAttribute("url","/consultings");
		model.addAttribute("alertType","success");
		return "/common/resultAlert";
	}
	
	@GetMapping("/consultings/detail/{con_num}")
	public String detail(@PathVariable Long con_num, Model model, HttpSession session) {
		ConsultingVO consulting = consultingService.getConsulting(con_num);
		consulting.setCon_rDate(DurationFromNow.getTimeDiffLabel(consulting.getCon_rDate()));
		model.addAttribute("consulting",consulting);
		
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum", 1);
		map.put("pageItemNum", 5);
		map.put("con_num", con_num);
		List<Con_ReVO> cReList = consultingService.getListCon_Re(map);

		model.addAttribute("cReList", cReList);
		
		boolean checkReply = false;
		Object user = session.getAttribute("user");
		if(user instanceof DoctorVO) {
			DoctorVO doctor = (DoctorVO)user;
			map.put("con_num", con_num);
			map.put("doc_num", doctor.getMem_num());
			checkReply = consultingService.isWriteReply(map);
			model.addAttribute("checkReply",checkReply);
		}
		return "cDetail";
	}
	
	@GetMapping("/consultings/detail-ajax")
	@ResponseBody
	public List<Con_ReVO> getReList(int pageNum, int pageItemNum, int con_num){
		Map<String,Object> map = new HashMap<>();
		map.put("pageNum", pageNum);
		map.put("pageItemNum", pageItemNum);
		map.put("con_num", con_num);
		List<Con_ReVO> cReList = consultingService.getListCon_Re(map);

		return cReList;
	}
	
	/*
	 * @GetMapping("/consultings/modify/{con_num}") public String update
	 * (@PathVariable Long con_num, Model model,HttpSession session) { ConsultingVO
	 * consulting = consultingService.getConsulting(con_num); Object user =
	 * session.getAttribute("user");
	 * 
	 * if(user!=null) { if(user.getClass().equals(MemberVO.class)) { MemberVO member
	 * = (MemberVO)user; if(member.getMem_num()==consulting.getMem_num()) {
	 * consultingService.modifyConsulting(consulting);
	 * model.addAttribute("consulting",consulting); return "cUpdate"; } else {
	 * model.addAttribute("url", "/consultings");
	 * model.addAttribute("message","본인 글이 아니면 수정할 수 없습니다."); return
	 * "/common/resultAlert"; } } else if(user.getClass().equals(DoctorVO.class)) {
	 * model.addAttribute("url", "/main/main");
	 * model.addAttribute("message","잘못된 접근입니다."); return "/common/resultAlert"; }
	 * else { return "404Error"; } } else { model.addAttribute("url",
	 * "/consultings"); model.addAttribute("message", "본인 글이 아니면 수정할 수 없습니다.");
	 * return "/common/resultAlert"; } }
	 */
	
	/*
	 * @PostMapping("/consultings/modify/{con_num}") public String update
	 * (@PathVariable Long con_num, ConsultingVO consulting, Model model) {
	 * 
	 * ConsultingVO oldConsulting = consultingService.getConsulting(con_num);
	 * oldConsulting.setCon_title(consulting.getCon_title());
	 * oldConsulting.setCon_content(consulting.getCon_content());
	 * oldConsulting.setCon_type(consulting.getCon_type());
	 * 
	 * consultingService.modifyConsulting(oldConsulting);
	 * 
	 * model.addAttribute("message","글이 성공적으로 수정되었습니다");
	 * model.addAttribute("url","/consultings");
	 * 
	 * return "/common/resultAlert"; }
	 */
	
	/*
	 * @GetMapping("/consultings/remove/{con_num}") public String remove(HttpSession
	 * session, @PathVariable Long con_num, Model model) { ConsultingVO consulting =
	 * consultingService.getConsulting(con_num);
	 * 
	 * Object user = session.getAttribute("user");
	 * 
	 * if(user!=null) { if(user.getClass().equals(MemberVO.class)) { MemberVO member
	 * = (MemberVO)user; if(member.getMem_num()==consulting.getMem_num()) {
	 * consultingService.removeConsulting(con_num); model.addAttribute("url",
	 * "/consultings"); model.addAttribute("message","글 삭제가 완료되었습니다."); return
	 * "/common/resultAlert"; } else { model.addAttribute("url", "/consultings");
	 * model.addAttribute("message","본인 글이 아니면 삭제할 수 없습니다."); return
	 * "/common/resultAlert"; } } else if(user.getClass().equals(DoctorVO.class)) {
	 * model.addAttribute("url", "/main/main");
	 * model.addAttribute("message","잘못된 접근입니다."); return "/common/resultAlert"; }
	 * else { return "404Error"; } } else { model.addAttribute("url",
	 * "/consultings"); model.addAttribute("message", "본인 글이 아니면 삭제할 수 없습니다.");
	 * return "/common/resultAlert"; } }
	 */
	
	@GetMapping("/consultings/detail/cReSat")
	@ResponseBody
	public Map<String,Object> satisfy(HttpSession session, Long cReNum, Long conNum, Long docNum) {
		Map<String,Object> map = new HashMap<>();
		
		ConsultingVO consulting = consultingService.getConsulting(conNum);
		Long mem_num = consulting.getMember().getMem_num();
		
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			Map<String,Object> re_map = new HashMap<>();
			map.put("status", "login");
			if(user instanceof MemberVO) {
				MemberVO member = (MemberVO)user;
				map.put("user_type","member");
				
				//본인 글
				if(member.getMem_num()==mem_num) {
					map.put("check", true);
					re_map.put("con_re_status", 1);
					re_map.put("con_re_num", cReNum);
					consultingService.modifyCon_Re_Status(re_map);
					
					NotificationVO noti = new NotificationVO();
					noti.setMem_num(docNum);
					noti.setNoti_category(5L);
					noti.setNoti_message("의료 상담 답글 상태가 변경되었습니다.(만족)");
					noti.setNoti_link("<a href='/consultings/detail/"+conNum+"'>내 답글 보러가기</a>");
					
					notificationService.insertNotification(noti);
				} else {
					map.put("check", false);
				}
			}else if(user instanceof DoctorVO) {
				map.put("user_type","doctor");
			} else {
				map.put("status", "error");
			}
		} else {
			map.put("status", "logout");
		}
		
		return map;
	}
	@GetMapping("/consultings/detail/cReUnSat")
	@ResponseBody
	public Map<String,Object> unSatisfy(HttpSession session, Long cReNum, Long conNum, Long docNum) {
		Map<String,Object> map = new HashMap<>();
		
		ConsultingVO consulting = consultingService.getConsulting(conNum);
		Long mem_num = consulting.getMember().getMem_num();
		
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			Map<String,Object> re_map = new HashMap<>();
			map.put("status", "login");
			if(user instanceof MemberVO) {
				MemberVO member = (MemberVO)user;
				map.put("user_type","member");
				
				//본인 글
				if(member.getMem_num()==mem_num) {
					map.put("check", true);
					re_map.put("con_re_status", 2);
					re_map.put("con_re_num", cReNum);
					consultingService.modifyCon_Re_Status(re_map);
					
					NotificationVO noti = new NotificationVO();
					noti.setMem_num(docNum);
					noti.setNoti_category(5L);
					noti.setNoti_message("의료 상담 답글 상태가 변경되었습니다.(불만족)");
					noti.setNoti_link("<a href='/consultings/detail/"+conNum+"'>내 답글 보러가기</a>");
					
					notificationService.insertNotification(noti);
				} else {
					map.put("check", false);
				}
			}else if(user instanceof DoctorVO) {
				map.put("user_type","doctor");
			} else {
				map.put("status", "error");
			}
		} else {
			map.put("status", "logout");
		}
		
		return map;
	}
	
	
	@PostMapping("/consultings/createReply")
	public String insert(Con_ReVO con_re, HttpSession session, Model model) {
		Object user = session.getAttribute("user");
		
		if(user!=null) {
			if(user.getClass().equals(MemberVO.class)) {
				model.addAttribute("url", "/main/main");
				model.addAttribute("message","일반 회원은 답글을 작성하실 수 없습니다.");
				model.addAttribute("alertType","warning");
				return "/common/resultAlert";
			} else if(user.getClass().equals(DoctorVO.class)) {
				DoctorVO doctor = (DoctorVO)user;
				con_re.setDoc_num(doctor.getDoc_num());
				con_re.setCon_re_content(StringUtil.useBrHtml(con_re.getCon_re_content()));
				consultingService.createCon_Re(con_re);
				
				Long mem_num = consultingService.getConsulting(con_re.getCon_num()).getMember().getMem_num();
				
				NotificationVO noti = new NotificationVO();
				noti.setMem_num(mem_num);
				noti.setNoti_category(5L);
				noti.setNoti_message("의료 상담 답글이 등록되었습니다.");
				noti.setNoti_link("<a href='/consultings/detail/"+con_re.getCon_num()+"'>답글 보러가기</a>");
				
				notificationService.insertNotification(noti);
				
				return "redirect:/consultings/detail/"+con_re.getCon_num();
			} else {
				return "404Error";
			}
		} else {
			model.addAttribute("url", "/doctor/login");
			model.addAttribute("message", "의사 회원만 이용 가능한 서비스 입니다.");
			model.addAttribute("alertType","warning");
			return "/common/resultAlert";
		}
	}
}
