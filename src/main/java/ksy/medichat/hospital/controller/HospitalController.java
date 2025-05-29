package ksy.medichat.hospital.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Filter;
import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @Value("${API.KSY.KAKAO-API-KEY}")
    private String apiKey;

    @GetMapping("")
    public String hospitals(Model model, HttpSession session, @RequestParam(required = false, defaultValue="37.4981646510326") Double user_lat, @RequestParam(required = false, defaultValue="127.028307900881") Double user_lon) {

        session.setAttribute("user_lat", user_lat);
        session.setAttribute("user_lon", user_lon);

        model.addAttribute("user_lat", user_lat);
        model.addAttribute("user_lon", user_lon);


        //카카오맵 api 키
        model.addAttribute("apiKey", apiKey);

        // 병원 (진료 과목) 리스트 생성 후 값 넣기
        List<List<String>> subList = new ArrayList<>();
        subList.add(new ArrayList<>(Arrays.asList("가정의학과", "건강증진, 예방, 만성질환 등", "home")));
        subList.add(new ArrayList<>(Arrays.asList("내과","감기, 소화기, 호흡기 등", "physician")));
        subList.add(new ArrayList<>(Arrays.asList("마취통증학과","급성통증, 만성통증 등", "syringe")));
        subList.add(new ArrayList<>(Arrays.asList("비뇨기과","소변 시 통증, 남성 질환 등", "urology")));
        subList.add(new ArrayList<>(Arrays.asList("산부인과","피임상담, 여성질환 등", "gynecology")));
        subList.add(new ArrayList<>(Arrays.asList("성형외과","피부질환, 화상, 상처 등", "beauty")));
        subList.add(new ArrayList<>(Arrays.asList("소아과","소아소화기, 소아호흡기, 알레르기 등", "child")));
        subList.add(new ArrayList<>(Arrays.asList("신경과","두통, 어지럼증, 뇌졸중 등", "brain")));
        subList.add(new ArrayList<>(Arrays.asList("신경외과","요통, 디스크, 신경계 질환 등", "headache")));
        subList.add(new ArrayList<>(Arrays.asList("안과","눈 피로, 결막염, 다래끼 등", "eye")));
        subList.add(new ArrayList<>(Arrays.asList("영상의학과","방사선 촬영, MRI, CT", "x-rays")));
        subList.add(new ArrayList<>(Arrays.asList("외과","갑상선, 유방, 하지정맥 등", "bone")));
        subList.add(new ArrayList<>(Arrays.asList("응급의학과","심한 탈수, 급성처치 등", "ambulance")));
        subList.add(new ArrayList<>(Arrays.asList("이비인후과","비염, 이명, 편도염 등", "ear")));
        subList.add(new ArrayList<>(Arrays.asList("재활의학과","신체회복, 물리치료, 만성통증 등", "medical")));
        subList.add(new ArrayList<>(Arrays.asList("정신건강의학과","수면장애, 스트레스, 중독 등", "mental")));
        subList.add(new ArrayList<>(Arrays.asList("정형외과","관절염, 골절, 척추 측만증 등", "surgical")));
        subList.add(new ArrayList<>(Arrays.asList("치과","치아질환, 잇몸질환, 턱 관절 등", "tooth")));
        subList.add(new ArrayList<>(Arrays.asList("피부과","두드러기, 가려움증, 탈모 등", "skin")));
        subList.add(new ArrayList<>(Arrays.asList("한의원","한방 진료, 다이어트, 경옥고 등", "treatment")));
        model.addAttribute("subList",subList);

        //어떻게 아프신가요?
        List<String> howSick = new ArrayList<>(Arrays.asList("독감","탈모","비염","대상포진","다이어트","아토피"));
        model.addAttribute("howSick", howSick);


        //List<String> dNameList = new ArrayList<>();
        //dNameList = deaseService.findAll();
        /*if(dNameList.size()>=10) {
            model.addAttribute("hotKeyWord", new ArrayList<>());
        } else {
            model.addAttribute("hotKeyWord", new ArrayList<>(Arrays.asList("여드름","지루성 피부염","감기","두드러기","역류성 식도염","보톡스","발열","백옥주사","당뇨")));
        }*/
        model.addAttribute("hotKeyWord", new ArrayList<>(Arrays.asList("여드름","지루성 피부염","감기","두드러기","역류성 식도염","보톡스","발열","백옥주사","당뇨")));

        // 지도에 병원 마커용
        Pageable pageable = PageRequest.of(1,10);
        Filter filter = Filter.builder().keyword("").sortType("NEAR").around(1000).user_lat(user_lat).user_lon(user_lon).build();
        model.addAttribute("hospitals", hospitalService.findHospitals(pageable,filter));

        return "/hospital/hospital";
    }




    @GetMapping("/search")
    public String search(Model model, HttpSession session,Filter filter,Pageable pageable) {
        model.addAttribute("hospitals",hospitalService.findHospitals(pageable, filter));

        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("HHmm")); //hh:mm
        int day = now.getDayOfWeek().getValue(); //1:월 2:화 3:수 4:목 5:금 6:토 7:일
        model.addAttribute("time", time);
        model.addAttribute("day", day);

        return "/hospital/search";
    }

    @ResponseBody
    @GetMapping("/search-json")
    public List<HospitalDTO> searchJson(Pageable pageable, Filter filter){
        return hospitalService.findHospitals(pageable, filter);
    }
}
