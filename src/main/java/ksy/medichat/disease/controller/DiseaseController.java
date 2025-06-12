package ksy.medichat.disease.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.disease.service.DiseaseService;
import ksy.medichat.filter.Filter;
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
        Filter filter = (Filter)session.getAttribute("filter");

        List<DiseaseDTO> list = diseaseService.getDiseases(pageable, filter);

        model.addAttribute("list", list);

        return "/disease/disease";
    }
}
