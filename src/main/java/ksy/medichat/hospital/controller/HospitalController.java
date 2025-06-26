package ksy.medichat.hospital.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.disease.service.DiseaseService;
import ksy.medichat.filter.Pageable;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Search search = (Search)session.getAttribute("search");
        session.setAttribute("search",search);

        //카카오맵 api 키
        model.addAttribute("apiKey", apiKey);
        // 병원 (진료 과목) 리스트 생성 후 값 넣기
        model.addAttribute("departmentList",hospitalService.getHospitalDepartment());
        //어떻게 아프신가요?
        model.addAttribute("howSick", diseaseService.getRandomDiseaseList(10,6));
        //조회수 높은 질병
        model.addAttribute("hotKeyWord", diseaseService.getTopDiseaseList(10,10));

        model.addAttribute("hospitals", hospitalService.findHospitals(search));
        return "hospital/hospital";
    }

    @GetMapping("/search")
    public String search(HttpSession session, Search inputSearch) {
        Search search = (Search) session.getAttribute("search");
        search.setKeyword(inputSearch.getKeyword());
        search.setCommonFilter(inputSearch.getCommonFilter());
        search.setSortType(inputSearch.getSortType());
        search.getPageable().setSize(20);
        session.setAttribute("search",search);
        return "hospital/search";
    }

    @ResponseBody
    @PostMapping("/search-json")
    public List<HospitalDTO> searchJson(HttpSession session, Pageable pageable){
        Search search = (Search) session.getAttribute("search");
        search.setPageable(pageable);
        return hospitalService.findHospitals(search);
    }

    @GetMapping("/search/{hosNum}")
    public String searchDetail(@PathVariable String hosNum, Model model) {
        //카카오맵 api 키
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("hospital", hospitalService.findHospital(hosNum));
        return "hospital/detail";
    }
}
