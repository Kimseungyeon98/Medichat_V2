package ksy.medichat.pharmacy.controller;

import ksy.medichat.pharmacy.service.PharmacyService;
import ksy.medichat.pharmacy.vo.PharmacyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class PharmacyController {
	@Value("${API.KSY.KAKAO-API-KEY}")
	private String apiKey;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping("/pharmacies/search")
	public String search(Model model,HttpSession session,@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="15") int pageItemNum, @RequestParam(defaultValue="") String keyword,
			@RequestParam(required = false) String commonFilter, @RequestParam(defaultValue="NEAR") String sortType,
			@RequestParam(defaultValue="37.4981646510326") String user_lat, @RequestParam(defaultValue="127.028307900881") String user_lon) {

		if(!user_lat.equals("37.4981646510326") && !user_lon.equals("127.028307900881")) {
			session.setAttribute("user_lat", user_lat);
			session.setAttribute("user_lon", user_lon);
		}
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("user_lat", user_lat);
		map.put("user_lon", user_lon);
		model.addAttribute("user_lat", user_lat);
		model.addAttribute("user_lon", user_lon);
		/* 나중에 위치정보 bean 만들어서 가져다 쓰기 지금은 귀찮... */
		log.debug("<<위치>> : " + user_lat + "," + user_lon);
		
		// 현재 시간 변수 생성 후 값 넣기
		LocalDateTime now = LocalDateTime.now();
		String time = now.format(DateTimeFormatter.ofPattern("HHmm")); //hh:mm
		int day = now.getDayOfWeek().getValue(); //1:월 2:화 3:수 4:목 5:금 6:토 7:일
		map.put("time", time);
		map.put("day", day);
		model.addAttribute("time", time);
		model.addAttribute("day", day);
		log.debug("<<시간>> = " + "day: " + day + ", time: " +time);
		
		// pageNum
		map.put("pageNum", pageNum);
		model.addAttribute("pageNum", pageNum);
		
		// pageItemNum
		map.put("pageItemNum", pageItemNum);
		model.addAttribute("pageItemNum", pageItemNum);
		
		// keyword
		map.put("keyword", keyword);
		model.addAttribute("keyword", keyword);
		
		// commonFilter
		map.put("commonFilter", commonFilter);
		model.addAttribute("commonFilter", commonFilter);
		
		//sortType
		map.put("sortType", sortType);
		model.addAttribute("sortType", sortType);
		
		// 병원 리스트 담기
		List<PharmacyVO> phaList = new ArrayList<>();
		phaList = pharmacyService.selectListPharmacy(map);
		model.addAttribute("phaList", phaList);
		
		return "pSearch";
	}
	
	@GetMapping("/pharmacies/search-json")
	@ResponseBody
	public List<PharmacyVO> searchJson(Model model,HttpSession session,@RequestParam(defaultValue="1") int pageNum,
							@RequestParam(defaultValue="15") int pageItemNum, @RequestParam(defaultValue="") String keyword,
							@RequestParam(required = false) String commonFilter, @RequestParam(defaultValue="NEAR") String sortType,
							@RequestParam(defaultValue="37.4981646510326") String user_lat, @RequestParam(defaultValue="127.028307900881") String user_lon){
		
		Map<String, Object> map = new HashMap<>();

		map.put("user_lat", user_lat);
		map.put("user_lon", user_lon);
		model.addAttribute("user_lat", user_lat);
		model.addAttribute("user_lon", user_lon);
		log.debug("<<위치>> : " + user_lat + "," + user_lon);
		
		// 현재 시간 변수 생성 후 값 넣기
		LocalDateTime now = LocalDateTime.now();
		String time = now.format(DateTimeFormatter.ofPattern("HHmm")); //hh:mm
		int day = now.getDayOfWeek().getValue(); //1:월 2:화 3:수 4:목 5:금 6:토 7:일
		map.put("time", time);
		map.put("day", day);
		model.addAttribute("time", time);
		model.addAttribute("day", day);
		log.debug("<<시간>> = " + "day: " + day + ", time: " +time);
		
		// pageNum
		map.put("pageNum", pageNum);
		model.addAttribute("pageNum", pageNum);
		
		// pageItemNum
		map.put("pageItemNum", pageItemNum);
		model.addAttribute("pageItemNum", pageItemNum);

		// keyword
		map.put("keyword", keyword);
		model.addAttribute("keyword", keyword);
		
		// commonFilter
		map.put("commonFilter", commonFilter);
		model.addAttribute("commonFilter", commonFilter);
		
		//sortType
		map.put("sortType", sortType);
		model.addAttribute("sortType", sortType);
		
		// 병원 리스트 담기
		List<PharmacyVO> phaList = new ArrayList<>();
		phaList = pharmacyService.selectListPharmacy(map);
		model.addAttribute("phaList", phaList);
		
		return phaList;
	}
	
	// 약국 > 검색 결과 > 상세 페이지
	@GetMapping("/pharmacies/search/detail/{pha_num}")
	public String detail(Model model, @PathVariable Long pha_num) {
		model.addAttribute("apiKey", apiKey);
		
		PharmacyVO pharmacy = pharmacyService.selectPharmacy(pha_num);
		model.addAttribute("pharmacy",pharmacy);
		
		
		pharmacyService.updateHitPharmacy(pha_num);
		
		// 현재 시간 변수 생성 후 값 넣기
		LocalDateTime now = LocalDateTime.now();
		String time = now.format(DateTimeFormatter.ofPattern("HHmm")); //hh:mm
		int day = now.getDayOfWeek().getValue(); //1:월 2:화 3:수 4:목 5:금 6:토 7:일
		model.addAttribute("time", time);
		model.addAttribute("day", day);
		log.debug("<<시간>> = " + "day: " + day + ", time: " +time);
		
		return "pDetail";
	}
}
