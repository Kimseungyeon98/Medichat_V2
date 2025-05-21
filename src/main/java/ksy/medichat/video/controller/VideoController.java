package ksy.medichat.video.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.util.PagingUtil;
import ksy.medichat.video.service.VideoService;
import ksy.medichat.video.vo.VideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class VideoController {

	@Autowired
	private VideoService service;

	@ModelAttribute
	public VideoVO initCommand() {
		return new VideoVO();
	}

	@GetMapping("/video/videoList")
	public String getVideoMain(@RequestParam(defaultValue="1") int pageNum,String keyfield,String keyword,Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("keyfield", keyfield);
		int count = service.selectCountV(map);
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,4,10,"videoList");
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		List<VideoVO> list = service.selectVList(map);
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("page",page.getPage());
		return "video_List";
	}

	@GetMapping("/video/videoWrite")
	public String getVideoWriteForm(HttpSession session,Model model,HttpServletRequest request) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else {
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				user_num= duser.getMem_num();
				return "video_Write";
			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
				if(mem.getMem_auth()>2) {
					return "video_Write";
				}else {
					model.addAttribute("message","쓰기 권한이 없습니다.");
					model.addAttribute("url",request.getContextPath()+"/video/videoList");
				}
			}	

		}

		return "common/resultAlert";

	}

	@PostMapping("/video/videoWrite")
	public String getVidoWrite(VideoVO vo,HttpSession session,HttpServletRequest request,Model model) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;

		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else  {
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				user_num= duser.getMem_num();
				vo.setMem_num(user_num);
				service.insertV(vo);
				return "redirect:/video/videoList";
			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
				if(mem.getMem_auth()>2) {
					vo.setMem_num(user_num);
					service.insertV(vo);
					return "redirect:/video/videoList";
				}else {
					model.addAttribute("message","쓰기 권한이 없습니다.");
					model.addAttribute("url",request.getContextPath()+"/video/videoList");
				}
			}
		}
		return "common/resultAlert";
	}
	@GetMapping("/video/videoDetail")
	public String getVdetail(Long video_num,Model model) {
		service.updateVhit(video_num);
		VideoVO vo = service.selectV(video_num);

		model.addAttribute("video", vo);


		return "video_Detail";
	}
	@GetMapping("/video/videoUpdate")
	public String getVupdateForm(Long video_num,HttpSession session,HttpServletRequest request,Model model) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;

		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else {
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				user_num= duser.getMem_num();

			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
				if(mem.getMem_auth()>2) {

				}else {
					model.addAttribute("message","수정 권한이 없습니다.");
					model.addAttribute("url",request.getContextPath()+"/video/videoList");
					return "common/resultAlert";
				}
			}
			VideoVO  db_vo = service.selectV(video_num);
			if(db_vo.getMem_num() == user_num) {
				model.addAttribute("videoVO",db_vo);
				log.debug("업데이트"+db_vo.getMem_num());
				return "video_Update";
			}
		}
		return "common/resultAlert";

	}

	@PostMapping("/video/videoUpdate")
	public String getVupdate(VideoVO videoVO,HttpSession session,HttpServletRequest request,Model model) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;

		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else  {
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				user_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
			}
			VideoVO db_vo = service.selectV(videoVO.getVideo_num());
			if(db_vo.getMem_num() == user_num) {
				service.updateV(videoVO);
				return "redirect:/video/videoDetail?video_num="+videoVO.getVideo_num();
			}else {
				model.addAttribute("message","수정 권한이 없습니다.");
				model.addAttribute("url",request.getContextPath()+"/video/videoList");
			}

			return "common/resultAlert";

		}
		return "common/resultAlert";
	}
	@GetMapping("/video/videoDelete")
	public String getVdelete(Long video_num,HttpSession session,HttpServletRequest request,Model model) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;

		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else {
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				user_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
			}

			VideoVO  db_vo = service.selectV(video_num);
			if(db_vo.getMem_num() == user_num) {
				service.deleteV(video_num);
				return "redirect:/video/videoList";
			}else {
				model.addAttribute("message","삭제 권한이 없습니다.");
				model.addAttribute("url",request.getContextPath()+"/video/videoList");
			}
		}
		return "common/resultAlert";
	}

}
