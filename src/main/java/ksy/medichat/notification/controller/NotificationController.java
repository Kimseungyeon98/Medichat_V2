package ksy.medichat.notification.controller;

import ksy.medichat.notification.domain.NotificationCategory;
import ksy.medichat.notification.dto.NotificationDTO;
import ksy.medichat.notification.service.NotificationService;
import ksy.medichat.user.domain.UserRole;
import ksy.medichat.user.dto.LoginUser;
import ksy.medichat.user.dto.UserDTO;
import ksy.medichat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/send")
    public String sendNotification(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        if(loginUser.getUserRole() == UserRole.ADMIN) {
            List<UserDTO> users = userService.findAll();
            model.addAttribute("users", users);
            model.addAttribute("categories", NotificationCategory.values());
            model.addAttribute("notification", new NotificationDTO());
            return "/notification/admin";
        } else {
            throw new AccessDeniedException("잘못된 접근");
        }
    }
    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@AuthenticationPrincipal LoginUser loginUser, NotificationDTO notification) {
        if(loginUser.getUserRole() == UserRole.ADMIN) {
            notificationService.save(notification);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/get")
    @ResponseBody
    public List<NotificationDTO> getNotifications(@AuthenticationPrincipal LoginUser loginUser) {
        List<NotificationDTO> notifications = new ArrayList<>();
        if(loginUser.getUserRole() == UserRole.USER) {
            UserDTO user = userService.findById(loginUser.getUsername());
            notifications = notificationService.findAllByUserCode(user.getCode()).stream().map(NotificationDTO::toDTO).collect(Collectors.toList());
        }
        return notifications;
    }

    @GetMapping("/getCount")
    @ResponseBody
    public Long getCountNotifications(@AuthenticationPrincipal LoginUser loginUser) {
        Long notificaitonsCount = 0l;
        if(loginUser.getUserRole() == UserRole.USER) {
            UserDTO user = userService.findById(loginUser.getUsername());
            notificaitonsCount = notificationService.countByUserCodeAndIsReadFalse(user.getCode());
        }
        return notificaitonsCount;
    }

    @GetMapping("/read")
    @ResponseBody
    public List<NotificationDTO> readNotification(@AuthenticationPrincipal LoginUser loginUser, Long code) {
        if(loginUser.getUserRole() == UserRole.USER) {
            notificationService.read(code);
        }
        return getNotifications(loginUser);
    }

    @GetMapping(value="/connect", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestHeader(value="LAST-EVENT-ID", required = false, defaultValue = "") final String lastEventId,
                                              @AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok(notificationService.connect(loginUser.getUsername(), lastEventId));
    }
}
