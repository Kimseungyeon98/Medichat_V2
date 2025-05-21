package ksy.medichat.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ksy.medichat.member.service.MemberService;
import ksy.medichat.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class NaverController {
	@Autowired
	private MemberService memberService;
	
	NaverAPI naverApi = new NaverAPI();

	@GetMapping("/member/naverlogin")
	public String authNaver(@RequestParam String code, @RequestParam String state,HttpSession session) {
	    String responseBody = naverApi.accessToken(naverApi.authCodeRequest(code, state)).getBody();
	    String accessToken = naverApi.extractAccessToken(responseBody);
	    
	    String userInfo =  naverApi.userInfo(naverApi.getUserInfo(accessToken)).getBody();
	    
	    // JSON 문자열을 JsonNode로 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(userInfo);
            String id = jsonNode.get("response").get("id").asText();
            String name = jsonNode.get("response").get("name").asText();
            String email = jsonNode.get("response").get("email").asText();
            String gender = jsonNode.get("response").get("gender").asText();
            
            session.setAttribute("mem_id", id);
            session.setAttribute("mem_name", name);
            session.setAttribute("mem_email", email);
            session.setAttribute("mem_gender", gender);
            
            MemberVO userExists = memberService.checkUser(id.toString());
            //DB에 해당 아이디로 등록된 사용자가 없으면 회원가입 폼으로 이동
            if(userExists == null) {
            	return "redirect:/member/registerNaver";
            }
            MemberVO member = null;
            member = memberService.checkId(id.toString());
            
            session.setAttribute("user", member);
            
            return "redirect:/main/main";
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //로그인 실패시 이동
        return "redirect:/member/login";
    }
	@GetMapping("/member/registerNaver")
	public String naverForm(Model model,HttpSession session) {
		
		String mem_id = (String)session.getAttribute("mem_id");
		String mem_name = (String)session.getAttribute("mem_name");
		String mem_email = (String)session.getAttribute("mem_email");
		
		MemberVO memberVO = new MemberVO();
		memberVO.setMem_id(mem_id);
		memberVO.setMem_name(mem_name);
		memberVO.setMem_email(mem_email);
		
		model.addAttribute("memberVO",memberVO);
		
		return "registerNaver";
	}
	@PostMapping("/member/registerNaver")
	public String naverSubmit(@Valid MemberVO memberVO,BindingResult result,Model model,
													HttpServletRequest request) {
		log.debug("<<네이버 회원가입>> : " + memberVO);
		//유효성 체크 결과 요류가 있으면 폼으로
		if(result.hasErrors()) {
			return "registerNaver";
		}
		//회원가입
		memberService.insertMember(memberVO);
		
		return "main";
	}
	@GetMapping("/member/naverLogout")
	public String naverLogout(HttpSession session,HttpServletResponse response) {
		//로그아웃
		session.removeAttribute("user");
		session.removeAttribute("mem_id");
		session.removeAttribute("mem_name");
		session.removeAttribute("mem_email");
		session.removeAttribute("mem_gender");
		
		return "redirect:/main/main";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}