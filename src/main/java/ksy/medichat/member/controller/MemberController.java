package ksy.medichat.member.controller;

import jakarta.validation.Valid;
import ksy.medichat.member.domain.Member;
import ksy.medichat.member.domain.MemberDetail;
import ksy.medichat.member.dto.MemberDTO;
import ksy.medichat.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/members")
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

        Map<String, String> mapAjax = new HashMap<>();

        boolean checkId = memberService.checkId(mem_id);
        if(checkId) {
            //중복O
            mapAjax.put("result", "idDuplicated");
        }else {
            //중복X
            if(!Pattern.matches("^[A-Za-z0-9]{4,12}$",mem_id)) {
                //패턴 틀림
                mapAjax.put("result", "notMatchPattern");
            }else {
                //미중복
                mapAjax.put("result", "idNotFound");
            }
        }
        return mapAjax;
    }

    // 로그인
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }
    @PostMapping("/login")
    public String login(@Valid MemberDTO member, BindingResult result) {
        return loginForm();
    }

    // 로그아웃
    @PostMapping
    public String logout(){
        return "redirect:/members";
    }

    // 특정 회원 상세 조회
    @GetMapping("/{id}")
    public String getMemberById(@PathVariable Long id){
        return null;
    }

    // 회원 정보 전체 수정
    @PutMapping("/{id}")
    public String updateMember(@PathVariable Long id, @Valid MemberDTO member, BindingResult result){
        return null;
    }

    // 회원 탈퇴(삭제)
    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id){
        return null;
    }
}
