package ksy.medichat.drug.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.drug.dto.DrugDTO;
import ksy.medichat.drug.service.DrugService;
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

@Controller
@Slf4j
@RequestMapping("/drugs")
public class DrugController {
    @Autowired
    private DrugService drugService;

    @GetMapping("")
    public String drugs(Model model, Pageable pageable, HttpSession session) {
        if(pageable==null){
            pageable = PageRequest.of(0,10);
        }
        Search search = (Search) session.getAttribute("search");
        if(search == null) {
            search = new Search();
        }
        session.setAttribute("search", search);

        List<DrugDTO> drugs = drugService.getDrugs(pageable, search);
        model.addAttribute("drugs", drugs);

        return "drug/drug";
    }
}
