package ksy.medichat;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Location;
import ksy.medichat.filter.Search;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<Void> initLocation(HttpSession session, Location inputLocation) {
		Search search = (Search)session.getAttribute("search");
		search.setLocation(inputLocation);
		session.setAttribute("search", search);
		return ResponseEntity.ok().build();
	}

}