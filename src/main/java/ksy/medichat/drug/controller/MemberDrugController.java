package ksy.medichat.drug.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.drug.service.MemberDrugService;
import ksy.medichat.drug.vo.DrugInfoVO;
import ksy.medichat.drug.vo.MemberDrugVO;
import ksy.medichat.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class MemberDrugController {
	@Autowired
	MemberDrugService memberDrugService;

	/*------회원 의약품 목록------*/
	//마이페이지 접근
	@GetMapping("/memberDrug/list")
	public String getDrugList(HttpSession session, Model model) {

		MemberVO user = (MemberVO)session.getAttribute("user");

		if(user == null) {
			model.addAttribute("message","로그인이 필요합니다.");
			model.addAttribute("url","/member/login");
			return "/common/resultAlert";
		}

		List<MemberDrugVO> memberDrug = memberDrugService.selectMemberDrugList(user.getMem_num());

		log.debug("<<회원 의약품 목록>> : " + memberDrug);

		model.addAttribute("memberDrug", memberDrug);

		return "memberDrugList";
	}

	/*------회원 의약품 등록------*/
	//의약품 목록 검색
	@GetMapping("/memberDrug/memberDrugSearchAjax")
	@ResponseBody
	public Map<String, Object> memberDrugSearchAjax(String drg_name){

		Map<String, Object> mapJson = new HashMap<String, Object>();

		List<DrugInfoVO> drugList = memberDrugService.selectDrugList(drg_name);

		mapJson.put("result", "success");
		mapJson.put("drugList", drugList);

		return mapJson;
	}

	//의약품 등록
	@GetMapping("/memberDrug/memberDrugInsertAjax")
	@ResponseBody
	public Map<String, Object> memberDrugInsertAjax(MemberDrugVO memberDrug, HttpSession session){

		Map<String, Object> mapJson = new HashMap<String, Object>();
		MemberVO user = (MemberVO)session.getAttribute("user");


		if(user == null) {
			mapJson.put("result", "logout");
		}

		log.debug("<<의약품 등록>> : " + memberDrug);

		memberDrug.setMem_num(user.getMem_num());

		memberDrugService.insertDrug(memberDrug);

		mapJson.put("result", "success");

		return mapJson;
	}

	/*------회원 의약품 수정------*/
	@PostMapping("/MemberDrug/memberDrugUpdateAjax")
	@ResponseBody
	public Map<String, Object> memberDrugUpdateAjax(MemberDrugVO memberDrugVO, HttpSession session){

		log.debug("<<의약품 수정>> : " + memberDrugVO);

		Map<String, Object> mapJson = new HashMap<String, Object>();

		MemberVO user = (MemberVO)session.getAttribute("user");

		MemberDrugVO db_drug = (MemberDrugVO) memberDrugService.selectDrug(memberDrugVO.getMed_num());
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else if(user!=null && user.getMem_num()==db_drug.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			memberDrugService.updateDrug(memberDrugVO);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}

		return mapJson;
	}

	/*------회원 의약품 삭제------*/
	@GetMapping("/MemberDrug/memberDrugDeleteAjax")
	@ResponseBody
	public Map<String, Object> memverDrugDeleteAjax(long med_num, HttpSession session){
		log.debug("<<의약품 삭제>> : ");
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		MemberDrugVO db_drug = memberDrugService.selectDrug(med_num);
		
		if(user==null) {
			mapJson.put("result", "logout");
		}else if(user!=null && user.getMem_num()==db_drug.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 일치
			memberDrugService.deleteDrug(med_num);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
}
