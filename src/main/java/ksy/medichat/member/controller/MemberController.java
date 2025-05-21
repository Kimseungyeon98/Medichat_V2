 package ksy.medichat.member.controller;

 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
 import jakarta.validation.Valid;
 import ksy.medichat.consulting.vo.ConsultingVO;
 import ksy.medichat.member.service.MemberService;
 import ksy.medichat.member.vo.MemberVO;
 import ksy.medichat.notification.service.NotificationService;
 import ksy.medichat.reservation.service.ReservationService;
 import ksy.medichat.util.AuthCheckException;
 import ksy.medichat.util.CaptchaUtil;
 import ksy.medichat.util.FileUtil;
 import ksy.medichat.util.PagingUtil;
 import lombok.extern.slf4j.Slf4j;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestParam;

 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

@Slf4j
@Controller
public class MemberController {
	@Autowired 
	MemberService memberService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	private ReservationService reservationService;
	
	@Value("${API.KCY.X-Naver-Client-Id}")
	private String X_Naver_Client_Id;
	@Value("${API.KCY.X-Naver-Client-Secret}")
	private String X_Naver_Client_Secret;

	//===========회원가입===========
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	//폼 호출
	@GetMapping("/member/registerUser")
	public String form() {
		return "memberRegister";
	}
	//회원가입 처리
	@PostMapping("/member/registerUser")
	public String submit(@Valid MemberVO memberVO,BindingResult result,HttpServletRequest request,Model model,
									HttpSession session) {
		log.debug("<회원가입> : " + memberVO);

		//유효성 체크 결과 오류가 있다면 다시 폼으로
		if(result.hasErrors()) {
			return form();
		}
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = memberVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		//변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return form();
		}
		//========캡챠 끝=============//
		
		//회원가입
		memberService.insertMember(memberVO);
		
		model.addAttribute("message","성공적으로 회원가입 되었습니다.");
		model.addAttribute("url",request.getContextPath()+"/main/main");
		model.addAttribute("alertType","success");
		
		return "common/resultAlert";
	}
	//===========로그인===========
	@GetMapping("/member/login")
	public String loginForm() {
		return "memberLogin";
	}
	//전송된 데이터 처리
	@PostMapping("/member/login")
	public String loginSubmit(@Valid MemberVO memberVO,BindingResult result,
											HttpServletRequest request,HttpSession session,
											HttpServletResponse response) {
		log.debug("<로그인 정보> : " +memberVO);
		
		//아이디와 비밀번호만 유효성 체크하여 오류가 있다면 폼으로
		if(result.hasFieldErrors("mem_id") || result.hasFieldErrors("mem_passwd")) {
			return loginForm();
		}
		MemberVO member = null;
		try {
			member = memberService.checkId(memberVO.getMem_id());
			boolean check = false;
			if(member!=null && member.getMem_passwd() != null) {
				//비밀번호 일치확인
				check = member.checkPasswd(memberVO.getMem_passwd());
			}else {
				result.reject("notFoundUser");
				return loginForm();
			}
			if(check) {
				//=====자동 로그인 시작=====
				/*
				 * boolean autoLogin = memberVO.getMem_auto()!=null &&
				 * memberVO.getMem_auto().equals("on"); if(autoLogin) { //자동 로그인 체크한 경우 String
				 * mem_au_id = member.getMem_au_id(); if(mem_au_id==null) { //자동로그인 체크 식별값 생성
				 * mem_au_id = UUID.randomUUID().toString(); log.debug("<<au_id>> : " +
				 * mem_au_id); member.setMem_au_id(mem_au_id);
				 * memberService.updateMem_au_id(member.getMem_au_id(),member.getMem_num()); }
				 * Cookie auto_cookie = new Cookie("au-log", mem_au_id);
				 * auto_cookie.setMaxAge(60*60*24*7);//쿠키 유효기간은 1주일 auto_cookie.setPath("/");
				 * 
				 * response.addCookie(auto_cookie); }
				 */
				//=====자동 로그인 끝=====
				//로그인 처리
				session.setAttribute("user", member);
				session.setAttribute("user_type", "membert");
				/* ksy 알림 처리 시작 */
				int noti_cnt = notificationService.selectCountNotification(member.getMem_num());
				session.setAttribute("noti_cnt", noti_cnt);
				/* ksy 알림 처리 끝 */
				
				log.debug("<인증 성공> : "+ member);

				return "redirect:/main/main";
			}
			//인증 실패
			throw new AuthCheckException();
		}catch(AuthCheckException e) {
			//인증 실패
			if(member!=null && member.getMem_auth()==1) {
				result.reject("noAuthority");
			}else {
				result.reject("invalidIdOrPassword");
			}
			log.debug("<인증 실패>");

			return loginForm();
		}
	}
	/*=============================
	 * 로그아웃
	 ============================*/
	@GetMapping("/member/logout")
	public String processLogout(HttpSession session,HttpServletResponse response) {	
		//로그아웃
		session.removeAttribute("user");
		//====== 자동로그인 체크 시작 =======//
		/*
		 * Cookie auto_cookie = new Cookie("au-log",""); auto_cookie.setMaxAge(0);//쿠키
		 * 삭제 auto_cookie.setPath("/");
		 * 
		 * response.addCookie(auto_cookie);
		 */
		//====== 자동로그인 체크 끝 =======//

		return "redirect:/main/main";
	}
	/*=============================
	 * 회원정보 수정
	 ============================*/
	//회정정보 수정 폼 호출
	@GetMapping("/member/modifyUser")
	public String updateForm(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         model.addAttribute("alertType","warning");
	         
	         return "common/resultAlert";
	    }
		MemberVO memberVO = memberService.selectMember(user.getMem_num());

		model.addAttribute("memberVO",memberVO);
		
		return "memberModify";
	}
	//회정정보 수정 폼에서 전송된 데이터 처리
	@PostMapping("/member/modifyUser")
	public String updateSubmit(@Valid MemberVO memberVO,BindingResult result,HttpSession session,Model model) {
		log.debug("<회원정보 수정> : " + memberVO);
		//유효성 체크
		if(result.hasErrors()) {
			return "memberModify";
		}
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		//회원정보 수정
		memberService.updateMember(memberVO);
		//세선에 저장된 정보 변경
		user.setMem_email(memberVO.getMem_email());

		model.addAttribute("message","정보가 수정되었습니다.");
        model.addAttribute("url","/member/myPage");
        model.addAttribute("alertType","success");
		
        return "common/resultAlert";
	}
	/*=============================
	 * 아이디 찾기
	 ============================*/
	@GetMapping("/member/memberFindId")
	public String findMemberIdForm() {
		return "memberFindId";
	}
	@PostMapping("/member/memberFindId")
	public String findMemberIdSubmit(@Valid MemberVO memberVO,BindingResult result,HttpSession session,Model model) {
		//유효성 체크
		if(result.hasFieldErrors("mem_name")||result.hasFieldErrors("mem_email")) {
			return "memberFindId";
		}
		MemberVO member = null;
		try {
			member = memberService.checkEmailAndName(memberVO.getMem_email(),memberVO.getMem_name());
			
			boolean check = false;
			if(member!=null && member.getMem_email() != null) {
				//이메일 일치확인
				check = member.checkEmail(memberVO.getMem_email());
				//이름 일치 확인
				check = member.checkName(memberVO.getMem_name());
			}else {
				result.reject("notFoundUser2");
				
				return "memberFindId";
			}
			if(check) {
				//아이디 찾기
				member = memberService.findId(memberVO);

				log.debug("<<아이디 찾기 결과>> : " + member.getMem_id());
				
				model.addAttribute("mem_id",member.getMem_id());
				
				return "/member/memberResultId";
			}
			//인증 실패
			throw new AuthCheckException();
		}catch(AuthCheckException e) {
			if(member!=null && member.getMem_auth() == 1) {
				result.reject("noAuthority");
			}
			log.debug("<인증 실패>");

			return "memberFindId";
		}
	}
	
	/*=============================
	 * 비밀번호 찾기(이메일 전송)
	 ============================*/
	@GetMapping("/member/sendMemPassword")
	public String sendPasswordForm() {
		return "memberFindPassword";
	}
	/*=============================
	 * 비밀번호 변경
	 ============================*/
	@GetMapping("/member/changePasswd")
	public String changePasswdForm(HttpSession session,Model model) {
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         model.addAttribute("alertType","warning");
	         
	         return "common/resultAlert";
	    }
		return "memberChangePasswd";
	}
	@PostMapping("/member/changePasswd")
	public String changePasswdSubmit(MemberVO memberVO,BindingResult result,HttpSession session,
										Model model,HttpServletRequest request) {
		if(result.hasFieldErrors("now_passwd")||result.hasFieldErrors("mem_passwd")) {
			return "memberChangePasswd";
		}
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = memberVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		//변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return "memberChangePasswd";
		}
		//========캡챠 끝=============//
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		
		MemberVO db_member = memberService.selectMember(memberVO.getMem_num());
		
		//비밀번호 일치 여부 확인
		if(!db_member.getMem_passwd().equals(memberVO.getNow_passwd())) {
			result.rejectValue("now_passwd", "invalidPasswd");
			return "memberChangePasswd";
		}
		//비밀번호 수정
		memberService.updatePasswd(memberVO);
		//자동 로그인 해제
		//memberService.deleteMem_au_id(memberVO.getMem_num());
		
		model.addAttribute("message","비밀번호가 수정되었습니다.");
		model.addAttribute("url","/member/myPage");
		model.addAttribute("alertType","success");
		
		return "common/resultAlert";
	}
	/*=============================
	 * 회원탈퇴
	 ============================*/
	@GetMapping("/member/deleteUser")
	public String deleteForm(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
	         model.addAttribute("message","로그인이 필요합니다.");
	         model.addAttribute("url","/member/login");
	         model.addAttribute("alertType","warning");
	         
	         return "common/resultAlert";
	    }
		return "memberDelete";
	}
	//탈퇴 폼에서 전송된 데이터 처리
	@PostMapping("/member/deleteUser")
	public String deleteSubmit(@Valid MemberVO memberVO,BindingResult result,HttpSession session,Model model,
													HttpServletRequest request) {
		
		//아이디와 비밀번호만 유효성 체크하여 오류가 있다면 폼으로
		if(result.hasFieldErrors("mem_id") || result.hasFieldErrors("mem_passwd")
												||result.hasFieldErrors("mem_email")) {
			return "memberDelete";
		}
		
		//========캡챠 시작=============//
		String code = "1";//키 발급 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키값
		String key = (String)session.getAttribute("captcha_key");
		//사용자가 입력한 캡챠 이미지 글자값
		String value = memberVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + key + "&value=" + value;

		Map<String, String> requestHeaders = new HashMap<String, String>();

		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);

		log.debug("<<캡챠 결과>> : " + responseBody);

		//변환 작업
		JSONObject jObject = new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars", "invalidCaptcha");
			return "memberDelete";
		}
		//========캡챠 끝=============//
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		memberVO.setMem_num(user.getMem_num());
		//회원 탈퇴
		memberService.deleteMember(memberVO.getMem_num());
		memberService.deleteMember_detail(memberVO);
		
		session.invalidate();
		
		model.addAttribute("message","회원탈퇴 되었습니다.");
		model.addAttribute("message2","그 동안 MediChat을 이용해주셔서 감사합니다.");
		model.addAttribute("url",request.getContextPath()+"/main/main");
		model.addAttribute("alertType","success");
		
        return "common/resultAlert";
	}
	/*=============================
	 * 관리자 회원등급 수정
	 ============================*/
	@GetMapping("/member/changeAuth")
	public String changeAuth(@RequestParam(defaultValue="1") int pageNum,
							String keyfield,String keyword,Long mem_num,HttpSession session, Model model,
															HttpServletRequest request) {
		MemberVO user = (MemberVO) session.getAttribute("user");
		if (user == null || user.getMem_auth() != 9) {
			model.addAttribute("message","접근 권한이 없습니다.");
			model.addAttribute("url",request.getContextPath()+"/main/main");
			model.addAttribute("alertType","warning");
			
			return "common/resultAlert";
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("keyfield", keyfield);
			map.put("keyword", keyword);
			
			//레코드수 
			int count = memberService.selectRowCount(map);
			//페이지 처리
			PagingUtil page = new PagingUtil(keyfield, keyword, pageNum,count,20,10,"list");
			
			List<MemberVO> memList = null;
			if(count > 0) {
				map.put("start", page.getStartRow());
				map.put("end", page.getEndRow());
				
				memList = memberService.getMemList(map);
			}

			model.addAttribute("count",count);
			model.addAttribute("memList", memList);
			model.addAttribute("page",page.getPage());
		}
		return "adminChangeAuth";
	}
	
	/*=============================
	 * 프로필 사진 출력
	 ============================*/
	//프로필 사진 출력(로그인 전용)
	@GetMapping("/member/memPhotoView")
	public String getProfile(HttpSession session,HttpServletRequest request,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		log.debug("<<프로필 사진 출력>> : " + user);
		if(user==null) {//로그인X
			getBasicProfileImage(request, model);//기본이미지 불러오기
		}else {//로그인O
			MemberVO memberVO = memberService.selectMember(user.getMem_num());
			
			memViewProfile(memberVO, request, model);
		}
		return "imageView";
	}
	
	//프로필 사진 출력(회원번호 지정)
	@GetMapping("/member/memViewProfile")
	public String getProfileByMem_num(long mem_num,HttpServletRequest request,Model model) {
		MemberVO memberVO = memberService.selectMember(mem_num);
		
		memViewProfile(memberVO,request,model);
		
		return "imageView";
	}
	
	//프로필 사진 처리를 위한 공통 코드
	public void memViewProfile(MemberVO memberVO,HttpServletRequest request,Model model) {
		if(memberVO == null || memberVO.getMem_photoname() == null) {
			//DB에 저장된 프로필 이미지가 없기 때문에 기본 이미지 로딩
			getBasicProfileImage(request, model);
		}else {
			//업로드한 프로필 이미지 읽기
			model.addAttribute("imageFile",memberVO.getMem_photo());
			model.addAttribute("filename","face.png");
		}
		
	}
	//기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request,Model model) {
		
		byte[] readbyte = FileUtil.getBytes(request.getServletContext().getRealPath("/image_bundle/face.png"));
		
		model.addAttribute("imageFile",readbyte);
		model.addAttribute("filename","face.png");	
	}
	
	/*=============================
	 * 캡챠 API
	 ============================*/
	@GetMapping("/member/getCaptcha")
	public String getCaptcha(Model model,HttpSession session) {

		String code = "0";
		String key_apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;

		Map<String, String> requestHeaders = new HashMap<String, String>();


		requestHeaders.put("X-Naver-Client-Id", X_Naver_Client_Id);
		requestHeaders.put("X-Naver-Client-Secret", X_Naver_Client_Secret);

		String responseBody = CaptchaUtil.get(key_apiURL, requestHeaders);

		log.debug("<<responseBody>> : " + responseBody);

		JSONObject jObject = new JSONObject(responseBody);
		try {
			//https://openapi.naver.com/v1/captcha/nkey 호출로 받은 key값
			String key = jObject.getString("key");
			session.setAttribute("captcha_key", key);

			String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;

			byte[] response_byte = CaptchaUtil.getCaptchaImage(apiURL, requestHeaders);

			model.addAttribute("imageFile", response_byte);
			model.addAttribute("filename", "captcha.jpg");
		}catch(Exception e) {
			log.error(e.toString());
		}
		return "imageView";
	}
	/*=============================
	 * MyPage
    ============================*/
	@GetMapping("/member/myPage")
	public String process(HttpSession session, Model model) {
	    MemberVO user = (MemberVO) session.getAttribute("user");
	    if(user == null) {
	        return "redirect:/login";
	    }
	    try{
	        MemberVO member = memberService.selectMember(user.getMem_num());
	        if(member != null) {
	            int count = reservationService.selectCountByMem(user.getMem_num());
	            member.setReservationCount(count);
	            log.debug("<<MY페이지>> : " + member);
	            model.addAttribute("member", member);
	            model.addAttribute("count", count);
	            return "myPage";
	        }else{
	            return "redirect:/login";
	        }
	    }catch(Exception e) {
	        return "redirect:/login";
	    }
	}
	@GetMapping("/mypage/memberInfo")
    public String process1(HttpSession session,Model model) {
      MemberVO user = (MemberVO)session.getAttribute("user");
      if(user == null) {
         return "login";
      }
      //회원정보
      MemberVO member = memberService.selectMember(user.getMem_num());
      
      log.debug("<<MY페이지>> : " + member);
      
      model.addAttribute("member", member);
        
      return "memberInfo";
    }
	/*=============================
	 * 의료상담 내역(마이페이지)
    ============================*/
	@GetMapping("/mypage/myConsult")
	public String myConsult(HttpSession session,Model model) {
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user == null) {
			model.addAttribute("message","로그인이 필요합니다.");
			model.addAttribute("url","/member/login");
			model.addAttribute("alertType","warning");
			
	        return "common/resultAlert";
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			MemberVO member = new MemberVO();
			member.setMem_num(user.getMem_num());
			
			map.put("mem_num",member.getMem_num());
			
			List<ConsultingVO> consultList = memberService.consultList(map);
			
			model.addAttribute("consultList",consultList);
		}
		return "myConsult";
	}
}