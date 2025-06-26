package ksy.medichat.pharmacy.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Pageable;
import ksy.medichat.filter.Search;
import ksy.medichat.pharmacy.dto.PharmacyDTO;
import ksy.medichat.pharmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/pharmacies")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping("")
    public String pharmacies(HttpSession session, Search inputSearch) {
        Search search = (Search) session.getAttribute("search");
        search.setKeyword(inputSearch.getKeyword());
        search.setCommonFilter(inputSearch.getCommonFilter());
        search.setSortType(inputSearch.getSortType());
        search.getPageable().setSize(20);
        session.setAttribute("search",search);
        return "pharmacy/pharmacy";
    }

    @PostMapping("/search-json")
    @ResponseBody
    public List<PharmacyDTO> searchJson(HttpSession session, Pageable pageable){
        Search search = (Search) session.getAttribute("search");
        search.setPageable(pageable);
        return pharmacyService.findPharmacies(search);
    }
}
