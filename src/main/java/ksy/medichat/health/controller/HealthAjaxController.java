package ksy.medichat.health.controller;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.health.service.HealthyService;
import ksy.medichat.health.vo.HealthyBlogVO;
import ksy.medichat.health.vo.HealthyFavVO;
import ksy.medichat.health.vo.HealthyReFavVO;
import ksy.medichat.health.vo.HealthyReplyVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.notification.service.NotificationService;
import ksy.medichat.notification.vo.NotificationVO;
import ksy.medichat.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HealthAjaxController {
	@Autowired 
	private HealthyService service ;

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/health/selectHReply")
	@ResponseBody
	public Map<String,Object> selectHreList(long healthy_num,@RequestParam(defaultValue="1") int pageNum,HttpSession session){

		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			user_num=0;
		}else{
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
				}
			
		}

		int count = service.selectHreCount(healthy_num);
		PagingUtil page = new PagingUtil(pageNum,count,5,10,null);
		map.put("healthy_num", healthy_num);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		map.put("user_num", user_num);
		List<HealthyReplyVO> list = service.selectHreList(map);

		Map<String,Object> jmap = new HashMap<String,Object>();
		jmap.put("list", list);
		jmap.put("count", count);
		jmap.put("user_num", user_num);

		return jmap;
	}


	@PostMapping("/health/clickHFav")
	@ResponseBody
	public Map<String,Object> clickFav(long healthy_num,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			map.put("result", "logout");
		}else {
			if(user_type.equals("membert")) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}		
			HealthyFavVO userfav= new HealthyFavVO();
			userfav.setHealthy_num(healthy_num);
			userfav.setMem_num(user_num);
			HealthyFavVO fav = service.selectHFav(userfav);
			if(fav == null) {
				service.insertHFav(userfav);
				map.put("status", "yesFav");
			}else {
				service.deleteHFav(userfav);
				map.put("status", "noFav");
			}
			map.put("result", "success");
			map.put("count",service.selectHFavCount(healthy_num));
		}

		return map;
	}
	@PostMapping("/health/insertHreply")
	@ResponseBody
	public Map<String,Object> insertHre(HealthyReplyVO vo, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			map.put("result", "logout");
		}else {
			if(user_type.equals("membert") ) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}		
			NotificationVO notice = new NotificationVO ();
			map.put("healthy_num", vo.getHealthy_num());
			map.put("user_num",user_num);
			HealthyBlogVO health = service.getHealthy(map);
			notice.setMem_num(health.getMem_num());
			notice.setNoti_category(2L);
			notice.setNoti_message(health.getHealthy_title()+" 게시글에 댓글이 달렸습니다.");
			notice.setNoti_link("<a href='/health/healthDetail?healthy_num="+health.getHealthy_num()+"'>"+health.getHealthy_title()+" 게시글 바로가기 </a>");
			notificationService.insertNotification(notice);
			vo.setMem_num(user_num);
			service.insertHre(vo);
			map.put("result", "success");
		}

		return map;
	}
	@PostMapping("/health/updateHReply")
	@ResponseBody
	public Map<String,Object> updateHre(HealthyReplyVO vo, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			map.put("result", "logout");
		}else{
			if(user_type.equals("membert")) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}	
			if(user_num == vo.getMem_num()) {
				service.updateHre(vo);
				map.put("result", "success");
			}else {
				map.put("result", "notWriter");}
		}

		return map;
	}
	@PostMapping("/health/deleteHreply")
	@ResponseBody
	public Map<String,Object> deleteHre(HealthyReplyVO vo, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			map.put("result", "logout");
		}else {
			if(user_type.equals("membert") ) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}		
			if(user_num == vo.getMem_num()) {
				service.deleteHre(vo.getHre_num());
				map.put("result", "success");
			}else {
				map.put("result", "notWriter");
			}
		}
		return map;
	}

	@PostMapping("/health/clickHreFav")
	@ResponseBody
	public Map<String,Object> clickHreFav(long hre_num,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			map.put("result", "logout");
		}else {
			if(user_type.equals("membert")) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}	
			HealthyReFavVO userfav= new HealthyReFavVO();
			userfav.setHre_num(hre_num);
			userfav.setMem_num(user_num);
			HealthyReFavVO fav = service.selectHreFav(userfav);
			if(fav == null) {
				service.insertHreFav(userfav);
				map.put("status", "yesFav");
			}else {
				service.deleteHreFav(userfav);
				map.put("status", "noFav");
			}
			map.put("result", "success");
			map.put("count",service.selectHreFavCount(hre_num));
		}

		return map;
	}

	//답글 달기
	@PostMapping("/health/writereHReply")
	@ResponseBody
	public Map<String,Object> writereHre(HealthyReplyVO vo, HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			map.put("result", "logout");
		}else {
			if(user_type.equals("membert") ) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}		
			
			NotificationVO notice = new NotificationVO ();

			HealthyReplyVO health = service.selectHre(vo.getHre_renum());
			notice.setMem_num(health.getMem_num());
			notice.setNoti_category(2L);
			notice.setNoti_message(health.getHre_content()+" 댓글에 답글이 달렸습니다.");
			Map<String,Object> hmap = new HashMap<String,Object>();
			hmap.put("healthy_num", vo.getHealthy_num());
			hmap.put("user_num",user_num);
			HealthyBlogVO healthy = service.getHealthy(hmap);
			notice.setNoti_link("<a href='../health/healthDetail?healthy_num="+health.getHealthy_num()+"'>"+healthy.getHealthy_title()+" 게시글 바로가기 </a>");
			notificationService.insertNotification(notice);
			vo.setMem_num(user_num);

			service.insertHre(vo);
			map.put("result", "success");
		}

		return map;
	}

	//답글 보기
	@PostMapping("/health/selectReHreply")
	@ResponseBody
	public Map<String,Object> selectReHreList(long hre_renum,HttpSession session){

		Map<String,Object> map = new HashMap<String,Object>();
		Object user_type = (Object)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			user_num=0;
		}else{
			if(user_type.equals("membert") ) {
				MemberVO mem = (MemberVO) user;
				map.put("user_num", mem.getMem_num());
				user_num= mem.getMem_num();
			}else {
				
				DoctorVO duser = (DoctorVO)user;
				map.put("user_num", duser.getMem_num());
				user_num= duser.getMem_num();
			}	
		}
		map.put("hre_renum", hre_renum);
		map.put("user_num", user_num);
		List<HealthyReplyVO> list = service.selectReHreList(map);

		Map<String,Object> jmap = new HashMap<String,Object>();
		jmap.put("list", list);
		jmap.put("user_num", user_num);
		log.debug("로그 댓글불러오기"+jmap);
		return jmap;
	}


}
