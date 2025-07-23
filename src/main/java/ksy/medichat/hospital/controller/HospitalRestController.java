package ksy.medichat.hospital.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.disease.service.DiseaseService;
import ksy.medichat.filter.Pageable;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalRestController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DiseaseService diseaseService;

    @Value("${API.KSY.KAKAO-API-KEY}")
    private String apiKey;

    @GetMapping("")
    public ResponseEntity<?> getHospitals(HttpSession session) {
        Search search = (Search)session.getAttribute("search");

        Map<String,Object> responseMap = new HashMap<>();
        //카카오맵 api 키
        responseMap.put("apiKey", apiKey);
        try{

            // 병원 (진료 과목) 리스트 생성 후 값 넣기
            responseMap.put("departmentList",hospitalService.getHospitalDepartment());
            // 어떻게 아프신가요?
            responseMap.put("howSick", diseaseService.getRandomDiseaseList(10,6));
            // 조회수 높은 질병
            responseMap.put("hotKeyWord", diseaseService.getTopDiseaseList(10,10));
            // 병원 리스트
            responseMap.put("hospitals", hospitalService.findHospitals(search));

        } catch (Exception e){
            responseMap.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearch(HttpSession session, Search inputSearch) {
        Search search = (Search) session.getAttribute("search");

        // 새로운 검색 조건 저장
        search.setKeyword(inputSearch.getKeyword());
        search.setCommonFilter(inputSearch.getCommonFilter());
        search.setSortType(inputSearch.getSortType());
        search.getPageable().setSize(20);

        // 저장한 검색 조건 다시 세션에 저장
        session.setAttribute("search",search);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> postSearch(HttpSession session, Pageable pageable){
        Search search = (Search) session.getAttribute("search");
        search.setPageable(pageable);

        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("hospitalList", hospitalService.findHospitals(search));
        } catch (Exception e){
            responseMap.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/search/{code}")
    public ResponseEntity<?> getSearchDetail(@PathVariable String code, Model model) {
        Map<String,Object> responseMap = new HashMap<>();
        //카카오맵 api 키
        responseMap.put("apiKey", apiKey);
        try{
            responseMap.put("hospital", hospitalService.findHospital(code));
        } catch (Exception e){
            responseMap.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(responseMap);
        }
        return ResponseEntity.ok(responseMap);
    }
}
