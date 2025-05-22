package ksy.medichat.member.controller;

import jakarta.validation.Valid;
import ksy.medichat.member.domain.Member;
import ksy.medichat.member.domain.MemberDetail;
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

    @ModelAttribute
    public Member initMember() {
        return new Member();
    }
    @ModelAttribute
    public MemberDetail initMemberDetail() {
        return new MemberDetail();
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }
    @PostMapping("/member/login")
    public String loginSubmit(@Valid Member member, @Valid MemberDetail memberDetail, BindingResult result) {
        return loginForm();
    }
}
