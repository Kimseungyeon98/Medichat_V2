package ksy.medichat.notification.service;

import jakarta.transaction.Transactional;
import ksy.medichat.notification.domain.Notification;
import ksy.medichat.notification.dto.NotificationDTO;
import ksy.medichat.notification.repository.EmitterRepository;
import ksy.medichat.notification.repository.NotificationRepository;
import ksy.medichat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    /*SSE 시작*/
    private final EmitterRepository emitterRepository;
    private final Long DEFAULT_TIMEOUT = 600_000L;


    public SseEmitter subscribe(Long userId, String lastEventId){
        // 매 연결마다 고유 이벤트 id 부여
        String eventId = makeTimeIncludeId(userId);

        // SseEmitter 인스턴스 생성 후 Map에 저장
        SseEmitter emitter = emitterRepository.save(eventId, new SseEmitter(DEFAULT_TIMEOUT));

        // 이벤트 전송 시
        emitter.onCompletion(() -> {
            System.out.println("onCompletion callback");
            emitterRepository.deleteById(eventId);
        });

        // 이벤트 스트림 연결 끊길 시
        emitter.onTimeout(() -> {
            System.out.println("onTimeout callback");
            emitter.complete();
            emitterRepository.deleteById(eventId);
        });

        // 첫 연결 시 503 Service Unavailable 방지용 더미 Event 전송
        sendNotification(emitter, eventId, eventId, "EventStream Created. [userId=%d]".formatted(userId));

        if(!lastEventId.isEmpty()){
            Map<String, Object> eventCaches = emitterRepository.findAllEventCacheByUserId(String.valueOf(userId));
            eventCaches.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendNotification(emitter, entry.getKey(), eventId, entry.getValue()));
        }

        return emitter;
    }

    public void send(User receiver, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.save(NotificationDTO.toEntity(notificationDTO));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = makeTimeIncludeId(receiver.getCode());
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterByUserId(receiverId);
        emitters.forEach(
                (id, emitter) -> {
                    emitterRepository.saveEventCache(id, notification);
                    sendNotification(emitter, eventId, id, notification);
                }
        );
    }

    private String makeTimeIncludeId(Long id) {
        return id + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
        }
    }

    /*SSE 끝*/

    public Notification save(NotificationDTO notificationDTO) {
        return notificationRepository.save(NotificationDTO.toEntity(notificationDTO));
    }

    public Notification read(Long code) {
        Notification notification = notificationRepository.findById(code).orElse(null);
        if(notification != null) {
            notification.setIsRead(true);
        }
        return notificationRepository.save(notification);
    }

    public Notification findById(Long code) {
        return notificationRepository.findById(code).orElse(null);
    }

    public Long countByUserCode(Long userCode) {
        return notificationRepository.countByUserCode(userCode);
    }

    public List<Notification> findAllByUserCode(Long userCode) {
        return notificationRepository.findAllByUserCode(userCode);
    }

    public void deleteByCode(Long code) {
        if(notificationRepository.existsById(code)){
            notificationRepository.deleteById(code);
        }
    }

    public Long countByUserCodeAndIsReadFalse(Long userCode){
        return notificationRepository.countByUserCodeAndIsReadFalse(userCode);
    }

}
