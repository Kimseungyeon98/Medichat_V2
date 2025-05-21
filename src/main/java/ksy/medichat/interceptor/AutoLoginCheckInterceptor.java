package ksy.medichat.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import ksy.medichat.member.service.MemberService;
import ksy.medichat.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Slf4j
public class AutoLoginCheckInterceptor implements HandlerInterceptor{
	@Autowired
	MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			                 HttpServletResponse response,
			                 Object handler)throws Exception{
		log.debug("<<AutoLoginCheckInterceptor 진입>>");
		
		HttpSession session = request.getSession();
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			Cookie now_cookie = findCookie(request.getCookies(),"au-log");
			if(now_cookie!=null) {
				MemberVO memberVO = memberService.selectMem_au_id(
						                           now_cookie.getValue());
				log.debug("<<자동로그인 여부 체크 MemberVO>> : " + memberVO);
				if(memberVO != null && memberVO.getMem_auth()>=2) {
					//일반회원부터 자동로그인 처리
					session.setAttribute("user", memberVO);
					log.debug("<<자동로그인 성공>>");
				}
			}
		}
		
		return true;
	}
	
	private Cookie findCookie(Cookie[] cookies, String name) {
		if(cookies == null || cookies.length == 0) {
			return null;
		}else {
			for(int i=0;i<cookies.length;i++) {
				String cookie_name = cookies[i].getName();
				if(cookie_name.equals(name)) {
					return cookies[i];
				}
			}
			return null;
		}
	}
	
}










