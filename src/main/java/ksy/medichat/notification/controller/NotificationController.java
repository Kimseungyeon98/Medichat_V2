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

    //Last-Event-ID는 SSE 연결이 끊어졌을 경우, 클라이언트가 수신한 마지막 데이터의 id 값을 의미합니다. 항상 존재하는 것이 아니기 때문에 false
    @GetMapping(value="/subscribe", produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<SseEmitter> subscribe(@AuthenticationPrincipal LoginUser loginUser,
                                                @RequestHeader(value="LAST-EVENT-ID", required = false, defaultValue = "") final String lastEventId) {
        return ResponseEntity.ok(notificationService.subscribe(loginUser.getUsername(), lastEventId));
    }

    // 알람 보내는 창
    @GetMapping("/form")
    public String send(@AuthenticationPrincipal LoginUser loginUser,
                       Model model) {
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

    // 알림 보내기(관리자 전용)
    @PostMapping("/form")
    public String sendNotificationByAdmin(@AuthenticationPrincipal LoginUser loginUser,
                                          NotificationDTO notification) {
        if(loginUser.getUserRole() == UserRole.ADMIN) {
            UserDTO receiver = userService.findByCode(notification.getUserCode());
            notificationService.send(receiver,notification);
        }
        return "redirect:/notifications/form";
    }

    // 전체 목록 불러오기
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<NotificationDTO>> getNotifications(@AuthenticationPrincipal LoginUser loginUser) {
        List<NotificationDTO> notifications = new ArrayList<>();
        if(loginUser.getUserRole() == UserRole.USER) {
            UserDTO user = userService.findById(loginUser.getUsername());
            notifications = notificationService.findAllByUserCode(user.getCode()).stream().map(NotificationDTO::toDTO).collect(Collectors.toList());
        }
        return ResponseEntity.ok(notifications);
    }

    // 읽지 않은 목록 갯수 불러오기
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Long> getNotificationsCount(@AuthenticationPrincipal LoginUser loginUser) {
        Long notificaitonsCount = 0l;
        if(loginUser.getUserRole() == UserRole.USER) {
            UserDTO user = userService.findById(loginUser.getUsername());
            notificaitonsCount = notificationService.countByUserCodeAndIsReadFalse(user.getCode());
        }
        return ResponseEntity.ok(notificaitonsCount);
    }

    // 읽음 처리
    @PatchMapping("/{code}")
    @ResponseBody
    public ResponseEntity<?> updateNotification(@AuthenticationPrincipal LoginUser loginUser,
                                                @PathVariable Long code) {
        if(loginUser.getUserRole() == UserRole.USER) {
            notificationService.read(code);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 삭제 처리
    @DeleteMapping("/{code}")
    @ResponseBody
    public ResponseEntity<?> deleteNotification(@AuthenticationPrincipal LoginUser loginUser,
                                                @PathVariable Long code) {
        if(loginUser.getUserRole() == UserRole.USER) {
            notificationService.deleteByCode(code);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
