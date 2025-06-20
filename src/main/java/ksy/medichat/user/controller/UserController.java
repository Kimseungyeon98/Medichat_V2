package ksy.medichat.member.controller;

import jakarta.validation.Valid;
import ksy.medichat.member.dto.MemberDTO;
import ksy.medichat.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/members")
@Slf4j
public class MemberController {

    @Autowired
    MemberService memberService;

    // Valid용 dto 초기화
    @ModelAttribute("member")
    public MemberDTO initMember() {
        return new MemberDTO();
    }

    // 회원가입 (회원 생성)
    @GetMapping("/register")
    public String registerMemberForm() {
        return "/member/register";
    }
    @PostMapping("/register")
    public String registerMember(@Valid @ModelAttribute("member") MemberDTO member, BindingResult result) {
        if (result.hasErrors()) {
            return registerMemberForm(); // 유효성 오류가 있으면 등록 폼으로 다시
        }
        memberService.saveMember(member); // 실제 등록 처리 (예: 회원 저장)

        return "redirect:/"; // 성공 시 홈으로 리다이렉트
    }
    //아아디 중복확인
    @GetMapping("/confirmId")
    @ResponseBody
    public Map<String,String> processMember(@RequestParam String mem_id){
        if(memberService.checkId(mem_id)) {
            //중복O
            return Map.of("result", "idDuplicated");
        }else {
            //중복X
            if(!Pattern.matches("^[A-Za-z0-9]{4,12}$",mem_id)) {
                //패턴 틀림
                return Map.of("result", "notMatchPattern");
            }else {
                //미중복
                return Map.of("result", "idNotFound");
            }
        }
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("member") MemberDTO member, BindingResult result) {
        if (result.hasErrors()) {
            return loginForm(); // 유효성 오류가 있으면 등록 폼으로 다시
        }
        return loginForm();
    }

    // 로그아웃
    @PostMapping
    public String logout(){
        return "redirect:/members";
    }

    @GetMapping("/user")
    @ResponseBody
    public ResponseEntity user(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userDetails.toString(), HttpStatus.OK);
    }
}
