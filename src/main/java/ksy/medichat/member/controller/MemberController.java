package ksy.medichat.member.controller;

import jakarta.validation.Valid;
import ksy.medichat.member.domain.Member;
import ksy.medichat.member.domain.MemberDetail;
import ksy.medichat.member.dto.MemberDTO;
import ksy.medichat.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    @ModelAttribute("member")
    public MemberDTO initMember() {
        return new MemberDTO();
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }
    @PostMapping("/member/login")
    public String loginSubmit(@Valid MemberDTO member, BindingResult result) {
        return loginForm();
    }

    @GetMapping("/member/register")
    public String registerForm() {
        return "member/register";
    }
    @PostMapping("/member/register")
    public String registerSubmit(@Valid MemberDTO member, BindingResult result) {
        return registerForm();
    }
}
