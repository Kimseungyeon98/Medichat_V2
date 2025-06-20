package ksy.medichat.user.controller;


import ksy.medichat.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserSecurityController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity user(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userDetails.toString(), HttpStatus.OK);
    }

    @GetMapping("/user1")
    @ResponseBody
    public ResponseEntity user1() {
        return new ResponseEntity<>("api1 입니다.",HttpStatus.OK);
    }

    @GetMapping("/user2")
    @ResponseBody
    public ResponseEntity user2() {
        return new ResponseEntity<>("user2 입니다.",HttpStatus.OK);
    }

}
