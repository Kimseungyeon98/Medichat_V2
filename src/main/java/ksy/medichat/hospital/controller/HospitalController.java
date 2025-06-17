package ksy.medichat.hospital.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.disease.service.DiseaseService;
import ksy.medichat.filter.Date;
import ksy.medichat.filter.Location;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Location location = (Location) session.getAttribute("location");
        Search search = new Search();
        search.setLocation(location);

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
        session.setAttribute("search", search);
        model.addAttribute("hospitals", hospitalService.findHospitals(pageable,search));

        log.info("<<hospitals>> {}",search);
        return "hospital/hospital";
    }

    @GetMapping("/search")
    public String search(HttpSession session, Model model, Pageable pageable, Search inputSearch) {
        Search search = (Search) session.getAttribute("search");
        search.setKeyword(inputSearch.getKeyword());
        search.setCommonFilter(inputSearch.getCommonFilter());
        search.setSortType(inputSearch.getSortType());
        session.setAttribute("search",search);

        pageable = PageRequest.of(pageable.getPageNumber(),20);
        model.addAttribute("pageable",pageable);

        log.info("<<search>> {}",search);
        return "hospital/search";
    }

    @ResponseBody
    @PostMapping("/search-json")
    public List<HospitalDTO> searchJson(Search search, Pageable pageable){

        log.info("{} Search",search);
        log.info("{} Page",pageable);
        log.info("<<search-json>>");
        return hospitalService.findHospitals(pageable, search);
    }

    @GetMapping("/search/{hosNum}")
    public String searchDetail(@PathVariable String hosNum, Model model) {
        //카카오맵 api 키
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("hospital", hospitalService.findHospital(hosNum));

        log.info("<<searchDetail>>");
        return "hospital/detail";
    }
}
