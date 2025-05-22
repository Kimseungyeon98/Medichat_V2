package ksy.medichat.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
public class MainController {
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main";
	}
	
	@GetMapping("/main")
	public String main(HttpSession session,Model model) {
		return "main/main";
	}
}