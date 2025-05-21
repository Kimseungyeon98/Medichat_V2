package ksy.medichat.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ksy.medichat.member.service.MemberService;
import ksy.medichat.member.vo.MemberVO;
import ksy.medichat.util.AuthCheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Slf4j
@Controller
public class KakaoController {
	@Autowired
	private MemberService memberService;
	
	KakaoAPI kakaoApi = new KakaoAPI();
		
	@RequestMapping(value="/member/kakaologin")
	public ModelAndView login(@RequestParam("code") String code,HttpSession session) throws AuthCheckException {
		ModelAndView mav = new ModelAndView();
		//1번 인증코드 요청 전달
		String accessToken = kakaoApi.getAccessToken(code);
		//2번 인증코드로 토큰 전달
		HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
		
		System.out.println("login info : " + userInfo.toString());
		
		if(userInfo.get("email") != null) {
			session.setAttribute("mem_id", userInfo.get("id").toString());
			session.setAttribute("mem_email", userInfo.get("email").toString());
			session.setAttribute("mem_name", userInfo.get("nickname").toString());
			session.setAttribute("mem_profile", userInfo.get("profile"));
			session.setAttribute("accessToken", accessToken);
		}
		//3번 DB에서 해당 사용자 정보가 있는지 확인
		MemberVO userExists = memberService.checkUser(userInfo.get("id").toString());
		//DB에 해당 이메일로 등록된 사용자가 없으면 회원가입 폼으로 이동
		if (userExists == null) {
		    mav.setViewName("redirect:/member/registerKakao");
		    return mav;
		}
		MemberVO member = null;
		member = memberService.checkId(userInfo.get("id").toString());
		
	    session.setAttribute("user", member);
	    
	    mav.setViewName("redirect:/main/main");
	    return mav;
	}
	@GetMapping("/member/registerKakao")
	public String kakaoform(Model model, HttpSession session) {
	    String mem_id = (String) session.getAttribute("mem_id");
	    String mem_name = (String) session.getAttribute("mem_name");
	    String mem_email = (String) session.getAttribute("mem_email");
	    
	    MemberVO memberVO = new MemberVO();
	    memberVO.setMem_id(mem_id);
	    memberVO.setMem_name(mem_name);
	    memberVO.setMem_email(mem_email);
	    
	    model.addAttribute("memberVO", memberVO);

	    return "registerKakao";
	}
	@PostMapping("/member/registerKakao")
	public String kakaoSubmit(@Valid MemberVO memberVO,BindingResult result,Model model,HttpServletRequest request) {
		log.debug("<<카카오 회원가입>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "registerKakao";
		}
		//회원가입
		memberService.insertMember(memberVO);
		
		return "main";
	}
	@GetMapping("/member/modifyKakao")
	public String kakaoModifyForm(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         return "/common/resultAlert";
	    }
		MemberVO memberVO = memberService.selectMember(user.getMem_num());

		model.addAttribute("memberVO",memberVO);

		return "kakaoModify";
	}
	@PostMapping("/member/modifyKakao")
	public String kakaoModifySubmit(@Valid MemberVO memberVO,BindingResult result,HttpSession session) {
		log.debug("<<카카오회원 정보 수정>> : " + memberVO);
		//유효성 체크
		if(result.hasErrors()) {
			return "kakaoModify";
		}
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		//회원정보 수정
		memberService.updateMember(memberVO);

		return "myPage";
	}
	
	@RequestMapping(value="/member/kakaologout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
		session.removeAttribute("accessToken");
		session.removeAttribute("mem_id");
		session.removeAttribute("mem_name");
		session.removeAttribute("mem_email");
		session.removeAttribute("mem_profile");
		session.removeAttribute("user");
		
		mav.setViewName("redirect:/main/main");
		return mav;
	}
}