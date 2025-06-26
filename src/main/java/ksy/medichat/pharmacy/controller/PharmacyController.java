package ksy.medichat.pharmacy.controller;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Date;
import ksy.medichat.filter.Location;
import ksy.medichat.filter.Search;
import ksy.medichat.pharmacy.dto.PharmacyDTO;
import ksy.medichat.pharmacy.service.PharmacyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/pharmacies")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping("")
    public String pharmacies(HttpSession session, Model model, Search inputSearch, Pageable pageable) {
        Search search = (Search) session.getAttribute("search");
        if(search==null){
            search = new Search();
        }
        Date date = search.getDate();
        if(date==null){
            date = new Date();
            ZonedDateTime koreaTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDateTime now = koreaTime.toLocalDateTime();
            date.setDay(now.getDayOfWeek().getValue()); // 1:월 ~ 7:일
            date.setTime(now.format(DateTimeFormatter.ofPattern("HHmm"))); // "hh:mm" → "HHmm" (24시간제)
            search.setDate(date);
        }
        search.setLocation((Location) session.getAttribute("location"));
        search.setKeyword(inputSearch.getKeyword());
        search.setCommonFilter(inputSearch.getCommonFilter());
        search.setSortType(inputSearch.getSortType());
        session.setAttribute("search",search);

        pageable = PageRequest.of(pageable.getPageNumber(),20);
        model.addAttribute("pageable",pageable);
        return "pharmacy/pharmacy";
    }

    @PostMapping("/search-json")
    @ResponseBody
    public List<PharmacyDTO> searchJson(Search search, Pageable pageable){
        return pharmacyService.findPharmacies(pageable, search);
    }
}
