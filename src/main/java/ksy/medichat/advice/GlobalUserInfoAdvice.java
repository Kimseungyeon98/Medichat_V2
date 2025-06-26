package ksy.medichat.advice;

import ksy.medichat.user.dto.LoginUser;
import ksy.medichat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalUserInfoAdvice {

    private final UserService userService;

    @ModelAttribute("loginUser")
    public LoginUser addLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 안 되어 있으면 null 반환
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        return (LoginUser)auth.getPrincipal(); // → DTO로 변환된 유저 정보
    }
}
