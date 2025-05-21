package ksy.medichat.faq.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.faq.service.FaqService;
import ksy.medichat.faq.vo.FaqVO;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.util.PagingUtil;
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
public class FaqController {

	@Autowired
	private FaqService service;

	@ModelAttribute
	public FaqVO initCommand() {
		return new FaqVO();
	}

	@GetMapping("/faq/faqList")
	public String getFMain(@RequestParam(defaultValue="1") int pageNum,String keyfield,String keyword,Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyword", keyword);
		map.put("keyfield", keyfield);
		int count = service.selectCountF(map);
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,8,10,"faqList");
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		List<FaqVO> list = service.selectFList(map);
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		model.addAttribute("keyfield", keyfield);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page",page.getPage());
		return "faq_List";
	}

	@GetMapping("/faq/faqWrite")
	public String getFaqWriteForm(HttpSession session,Model model,HttpServletRequest request) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else {
			if(user_type.equals("doctor")) {
				return "faq_Write";
			}else {
				MemberVO mem = (MemberVO) user;
				if(mem.getMem_auth()>2) {
					return "faq_Write";
				}else {
					model.addAttribute("message","쓰기 권한이 없습니다.");
					model.addAttribute("url",request.getContextPath()+"/faq/faqList");
				}

			}
		}
		return "common/resultAlert";
	}

	@PostMapping("/faq/faqWrite")
	public String getFaqWrite(FaqVO vo,HttpSession session,HttpServletRequest request,Model model) {
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
				vo.setMem_num(user_num);
				service.insertF(vo);
				return "redirect:/faq/faqList";
			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
				if(mem.getMem_auth()>2) {
					vo.setMem_num(user_num);
					service.insertF(vo);
					return "redirect:/faq/faqList";
				}else {
					model.addAttribute("message","쓰기 권한이 없습니다.");
					model.addAttribute("url",request.getContextPath()+"/faq/faqList");
				}
			}	
		}
		return "common/resultAlert";
	}
	@GetMapping("/faq/faqDetail")
	public String getFaqdetail(Long faq_num,Model model) {
		service.updateFhit(faq_num);
		FaqVO vo = service.selectF(faq_num);

		model.addAttribute("faq", vo);


		return "faq_Detail";
	}
	@GetMapping("/faq/faqUpdate")
	public String getFaqupdateForm(Long faq_num,HttpSession session,HttpServletRequest request,Model model) {
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
			FaqVO  db_vo = service.selectF(faq_num);
			if(db_vo.getMem_num() == user_num) {
				model.addAttribute("faqVO",db_vo);

				return "faq_Update";
			}else {
				model.addAttribute("message","수정 권한이 없습니다.");
				model.addAttribute("url",request.getContextPath()+"/faq/faqList");
			}
		}
		return "common/resultAlert";

	}

	@PostMapping("/faq/faqUpdate")
	public String getFaqupdate(FaqVO faqVO,HttpSession session,HttpServletRequest request,Model model) {
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
			FaqVO db_vo = service.selectF(faqVO.getFaq_num());
			if(db_vo.getMem_num() == user_num) {
				service.updateF(faqVO);
				return "redirect:/faq/faqDetail?faq_num="+faqVO.getFaq_num();
			}else {
				model.addAttribute("message","수정 권한이 없습니다.");
				model.addAttribute("url",request.getContextPath()+"/faq/faqList");
			}
		}
		return "common/resultAlert";

	}

	@GetMapping("/faq/faqDelete")
	public String getFaqdelete(Long faq_num,HttpSession session,HttpServletRequest request,Model model) {
		String user_type = (String)session.getAttribute("user_type");
		Object user = (Object) session.getAttribute("user");
		long user_num =0;

		if(user == null) {
			model.addAttribute("message","로그인 후 사용가능합니다.");
			model.addAttribute("url",request.getContextPath()+"/member/login");

		}else{
			if(user_type.equals("doctor")) {
				DoctorVO duser = (DoctorVO)user;
				user_num= duser.getMem_num();
			}else {
				MemberVO mem = (MemberVO) user;
				user_num= mem.getMem_num();
			}	
			FaqVO  db_vo = service.selectF(faq_num);
			if(db_vo.getMem_num() == user_num) {
				service.deleteF(faq_num);
				return "redirect:/faq/faqList";
			}else {
				model.addAttribute("message","삭제 권한이 없습니다.");
				model.addAttribute("url",request.getContextPath()+"/faq/faqList");
			}
		}
		return "common/resultAlert";

	}
}
