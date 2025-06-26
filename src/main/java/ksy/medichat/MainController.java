package ksy.medichat;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Location;
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
		session.setAttribute("location", inputLocation);
		return ResponseEntity.ok().build();
	}

}