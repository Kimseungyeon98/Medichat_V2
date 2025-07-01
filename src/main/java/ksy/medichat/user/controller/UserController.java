package ksy.medichat.user.controller;

import jakarta.validation.Valid;
import ksy.medichat.user.dto.UserDTO;
import ksy.medichat.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Valid용 dto 초기화
    @ModelAttribute("user")
    public UserDTO initUser() {
        return new UserDTO();
    }

    // 회원가입 (회원 생성)
    @GetMapping("/register")
    public String registerUserForm() {
        return "/user/register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDTO inputUser, BindingResult result) {
        if (result.hasErrors()) {
            return registerUserForm(); // 유효성 오류가 있으면 등록 폼으로 다시
        }
        UserDTO user = userService.save(inputUser); // 실제 등록 처리 (예: 회원 저장)
        return "redirect:/"; // 성공 시 홈으로 리다이렉트
    }
    //아아디 중복확인
    @GetMapping("/confirmId")
    @ResponseBody
    public Map<String,String> processUser(@RequestParam String mem_id){
        if(userService.checkId(mem_id)) {
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
        return "user/login";
    }

}
