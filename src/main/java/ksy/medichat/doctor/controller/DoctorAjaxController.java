package ksy.medichat.doctor.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.doctor.service.DoctorService;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;
import ksy.medichat.member.email.Email;
import ksy.medichat.member.email.EmailSender;
import ksy.medichat.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class DoctorAjaxController {
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private Email email;
	@Autowired
	private DoctorService doctorService;
	
	//아아디 중복확인
	@GetMapping("/doctor/confirmId")
	@ResponseBody
	public Map<String,String> processDoctor(@RequestParam String mem_id){
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		DoctorVO doctor = doctorService.checkId(mem_id);
		if(doctor!=null) {
			//중복O
			mapAjax.put("result", "idDuplicated");
		}else {
			//중복X
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$",mem_id)) {
				//패턴 틀림
				mapAjax.put("result", "notMatchPattern");
			}else {
				//미중복
				mapAjax.put("result", "idNotFound");
			}
		}
		return mapAjax;
	}
	//프로필 사진 업로드
	@PostMapping("/doctor/updateDocPhoto")
	@ResponseBody
	public Map<String, String> processProfile(DoctorVO doctorVO, HttpSession session){
		
		DoctorVO user = (DoctorVO)session.getAttribute("user");
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if(user == null) {
			mapAjax.put("result", "logout");
		}else {
			doctorVO.setMem_num(user.getMem_num());
			doctorService.uploadDocProfile(doctorVO);
			
			mapAjax.put("result","success");
		}
		return mapAjax;
	}
	/*===================
		병원 목록
	===================*/
	@PostMapping("/doctor/hosList")
	@ResponseBody
	public Map<String, Object> getList(@RequestParam(required = false) Long hos_num,
	                                   @RequestParam(required = false) String keyfield,
	                                   @RequestParam(required = false) String keyword) {
	    Map<String, Object> mapJson = new HashMap<>();
	    try {
	        // 검색 키워드를 이용하여 병원 목록을 조회하는 로직을 구현
	        List<HospitalVO> hosList = doctorService.getHosListByKeyword(keyword);

	        mapJson.put("hosList", hosList);
	        mapJson.put("success", true);
	    } catch (Exception e) {
	        mapJson.put("success", false);
	        mapJson.put("message", "병원 목록 조회 중 오류가 발생하였습니다.");
	        e.printStackTrace(); // 로깅 또는 예외처리
	    }
	    return mapJson;
	}
	/*===================
	  의사회원가입 관리자 승인
	===================*/
	@PostMapping("/updateAgree")
	@ResponseBody
	public Map<String, String> updateAgree(DoctorVO doctor,HttpSession session){
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		doctorService.updateAgree(doctor);
		
		mapJson.put("result", "success");
		
		return mapJson;
	}
	/*===================
	  비밀번호 이메일 전송
	===================*/
	@PostMapping("/doctor/getPasswordInfo")
	@ResponseBody
	public Map<String, String> sendEmailAction(DoctorVO doctorVO){
		log.debug("<<비밀번호 찾기>> : " + doctorVO);
		
		Map<String, String> mapJson = new HashMap<String, String>();
		
		DoctorVO doctor = doctorService.checkId(doctorVO.getMem_id());
		
		if(doctor!=null && doctor.getMem_auth()>1 && doctor.getDoc_email().equals(doctorVO.getDoc_email())) {
			//오류를 대비해서 원래 비밀번호 저장
			String origin_passwd = doctor.getDoc_passwd();
			//임시비밀번호로 변경
			String doc_passwd = StringUtil.randomPassword(10);
			doctor.setDoc_passwd(doc_passwd);
			//임시비밀번호 DB에 저장
			doctorService.findPasswd(doctor);
			
			email.setContent("임시 비밀번호는 "+doc_passwd+" 입니다.");
			email.setReceiver(doctor.getDoc_email());
			email.setSubject(doctor.getMem_id()+" 님 비밀번호 찾기 메일입니다.");
			try {
				emailSender.sendEmail(email);
				mapJson.put("result", "success");
				
			}catch(Exception e) {
				log.error("<<비밀번호 찾기>> : " + e.toString());
				//오류 발생시 비밀번호 원상복구
				doctor.setDoc_passwd(origin_passwd);
				doctorService.findPasswd(doctor);
				mapJson.put("result", "failure");
			}
		}else if(doctor!=null && doctor.getMem_auth()==1) {
			//정지회원
			mapJson.put("result", "noAuthority");
		}else {
			mapJson.put("result", "invalidInfo");
		}
		return mapJson;
	}
	
	//의사 연혁 가져오기
	@GetMapping("/doctor/doctorHistory")
	@ResponseBody
	public Map<String,Object> selectDoctorHistory(long hos_num){
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<DoctorVO> doctor = doctorService.docListByHosNum(hos_num);
		
		if(doctor.isEmpty()) {
			map.put("doctor", "empty");
		}else {
			map.put("doctor", doctor);
		}
		
		return map;
	}
}
