package ksy.medichat.main.controller;

import ksy.medichat.filter.Filter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping("/")
	public String init() {
		return "main";
	}

	@PostMapping("/initLocation")
	public ResponseEntity<Void> initLocation(HttpSession session, double user_lat, double user_lon) {
		Filter filter = (Filter)session.getAttribute("filter");
		if(filter == null){
			filter = new Filter();
			filter.setUser_lat(user_lat);
			filter.setUser_lon(user_lon);
			filter.setSortType("NEAR");
			filter.setAround(1000);
			session.setAttribute("filter", filter);
		}

		System.out.println("<<Main Controller>> " + filter);
		return ResponseEntity.ok().build();
	}
}