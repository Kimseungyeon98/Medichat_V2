package ksy.medichat.disease.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.disease.service.DiseaseService;
import ksy.medichat.filter.Search;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/diseases")
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @GetMapping("")
    public String diseases(Model model, HttpSession session, Pageable pageable) {
        if(pageable==null){
            pageable = PageRequest.of(0,10);
        }
        Search search = (Search) session.getAttribute("search");
        if(search == null) {
            search = new Search();
        }
        session.setAttribute("search", search);

        List<DiseaseDTO> diseases = diseaseService.getDiseases(pageable, search);

        model.addAttribute("diseases", diseases);

        return "/disease/disease";
    }
}
