package ksy.medichat;

import jakarta.servlet.http.HttpSession;
import ksy.medichat.filter.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
@Slf4j
public class MainController {

	@GetMapping("/")
	public String init() {
		log.info("<<init>>");
		return "main";
	}

	@PostMapping("/initLocation")
	public ResponseEntity<Void> initLocation(HttpSession session, Location inputLocation) {
		session.setAttribute("location", inputLocation);

		log.info("<<initLocation>> " + inputLocation);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/api1")
	@ResponseBody
	public ResponseEntity api1() {
		return new ResponseEntity<>("api1 입니다.",HttpStatus.OK);
	}

	@GetMapping("/api2")
	@ResponseBody
	public ResponseEntity api2() {
		return new ResponseEntity<>("api2 입니다.",HttpStatus.OK);
	}
}