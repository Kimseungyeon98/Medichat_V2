package ksy.medichat.interceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ksy.medichat.community.service.CommunityService;
import ksy.medichat.community.vo.CommunityVO;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
public class WriterCheckInterceptor implements HandlerInterceptor{
	@Autowired
	private CommunityService communityService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		log.debug("<<===로그인 회원번호와 작성자 회원번호 일치 여부 체크===>>");
		
		//로그인 회원번호
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		
		//작성자 회원번호
		long cbo_num = Long.parseLong(request.getParameter("cbo_num"));
		CommunityVO community = communityService.selectCommunity(cbo_num);

		
		if(user!=null) {
			if(user instanceof DoctorVO) {
				DoctorVO doctor = (DoctorVO) user;
				log.debug("<<로그인한 회원번호(의사)>> : " + doctor.getMem_num());
				log.debug("<<작성자 회원번호(의사)>> : " + community.getMem_num());
				
				if(doctor.getMem_num() != community.getMem_num()) {
					log.debug("<<로그인 회원번호와 작성자 아이디 불일치(의사)>>");
					
					request.setAttribute("message", "로그인한 아이디와 작성자 아이디가 불일치합니다");
					request.setAttribute("url", request.getContextPath()+"/medichatCommunity/list");
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/common/resultAlert.jsp");
					dispatcher.forward(request, response);
					
					return false;
				}
			}else if(user instanceof MemberVO) {
				MemberVO member = (MemberVO) user;
				log.debug("<<로그인한 회원번호(회원)>> : " + member.getMem_num());
				log.debug("<<작성자 회원번호(회원)>> : " + community.getMem_num());
				
				if(member.getMem_num() != community.getMem_num()) {
					log.debug("<<로그인 회원번호와 작성자 아이디 불일치(회원)>>");
					
					request.setAttribute("message", "로그인한 아이디와 작성자 아이디가 불일치합니다");
					request.setAttribute("url", request.getContextPath()+"/medichatCommunity/list");
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/resultAlert.jsp");
					dispatcher.forward(request, response);
					
					return false;
				}
			}//end of instanceof
		}else if(user == null){
			log.debug("<<로그아웃 상태>>");
			
			request.setAttribute("message", "로그인 후 이용해주세요");
			request.setAttribute("url", request.getContextPath()+"/medichatCommunity/list");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/resultAlert.jsp");
			dispatcher.forward(request, response);
			
			return false;
		}
		
		log.debug("<<로그인 회원번호와 작성자 회원번호 일치>>");
		
		return true;
	}
}
