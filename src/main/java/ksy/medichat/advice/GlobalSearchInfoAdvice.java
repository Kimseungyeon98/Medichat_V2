package ksy.medichat.advice;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Search;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalSearchInfoAdvice {

    @ModelAttribute("search")
    public Search addSearch(HttpSession session) {
        Search search = (Search) session.getAttribute("search");

        // Search 없으면 만들어서 넣어줌
        if (search == null) {
            search = new Search();
        }
        session.setAttribute("search", search);

        return search;
    }
}
