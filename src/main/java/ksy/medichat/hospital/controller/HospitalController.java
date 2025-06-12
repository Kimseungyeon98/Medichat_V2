package ksy.medichat.hospital.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.disease.service.DiseaseService;
import ksy.medichat.filter.Filter;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.dto.HospitalDepartmentDTO;
import ksy.medichat.hospital.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DiseaseService diseaseService;

    @Value("${API.KSY.KAKAO-API-KEY}")
    private String apiKey;

    @GetMapping("")
    public String hospitals(HttpSession session, Model model) {
        log.info("<<hospitals>>");
        Filter filter = (Filter)session.getAttribute("filter");
        if(filter == null){
            filter = new Filter();
        }

        // 지도에 병원 마커용
        Pageable pageable = PageRequest.of(0,10);
        //카카오맵 api 키
        model.addAttribute("apiKey", apiKey);
        // 병원 (진료 과목) 리스트 생성 후 값 넣기
        model.addAttribute("departmentList",hospitalService.getHospitalDepartment());
        //어떻게 아프신가요?
        model.addAttribute("howSick", diseaseService.getRandomDiseaseList(10,6));
        //조회수 높은 질병
        model.addAttribute("hotKeyWord", diseaseService.getTopDiseaseList(10,10));

        model.addAttribute("pageable", pageable);
        model.addAttribute("hospitals", hospitalService.findHospitals(pageable,filter));
        return "/hospital/hospital";
    }

    @GetMapping("/search")
    public String search(HttpSession session, Model model, Pageable pageable, String keyword, String commonFilter) {
        log.info("<<hospitals/search>>");
        Filter filter = (Filter)session.getAttribute("filter");
        if(filter == null){
            filter = new Filter();
        }
        filter.setKeyword(keyword);
        filter.setCommonFilter(commonFilter);

        LocalDateTime now = LocalDateTime.now();
        filter.setTime(now.format(DateTimeFormatter.ofPattern("HHmm")));//hh:mm
        filter.setDay(now.getDayOfWeek().getValue());//1:월 2:화 3:수 4:목 5:금 6:토 7:일
        session.setAttribute("filter",filter);

        pageable = PageRequest.of(pageable.getPageNumber(),20);
        model.addAttribute("pageable",pageable);
        model.addAttribute("hospitals",hospitalService.findHospitals(pageable, filter));
        return "/hospital/search";
    }

    @ResponseBody
    @GetMapping("/search-json")
    public List<HospitalDTO> searchJson(Filter filter, Pageable pageable){
        log.info("<<hospitals/search-json>>");
        log.info("filter:{}",filter);
        log.info("pageable:{}",pageable);
        return hospitalService.findHospitals(pageable, filter);
    }

    @GetMapping("/search/{hosNum}")
    public String searchDetail(@PathVariable String hosNum, HttpSession session, Model model) {
        log.info("<<hospitals/search/hosNum>>");
        Filter filter = (Filter)session.getAttribute("filter");
        if(filter == null){
            filter = new Filter();
        }

        LocalDateTime now = LocalDateTime.now();
        filter.setTime(now.format(DateTimeFormatter.ofPattern("HHmm")));//hh:mm
        filter.setDay(now.getDayOfWeek().getValue());//1:월 2:화 3:수 4:목 5:금 6:토 7:일
        session.setAttribute("filter",filter);

        //카카오맵 api 키
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("hospital", hospitalService.findHospital(hosNum));
        return "/hospital/detail";
    }
}
