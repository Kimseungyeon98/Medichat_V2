package ksy.medichat.notification.service;

import jakarta.transaction.Transactional;
import ksy.medichat.notification.domain.Notification;
import ksy.medichat.notification.dto.NotificationDTO;
import ksy.medichat.notification.repository.EmitterRepository;
import ksy.medichat.notification.repository.NotificationRepository;
import ksy.medichat.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    /* SSE 시작 */
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    //연결 지속시간 한 시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;


    public SseEmitter subscribe(String userId, String lastEventId){
        // 매 연결마다 고유 이벤트 id 부여
        String emitterId = userId + "_" + System.currentTimeMillis();

        // SseEmitter 인스턴스 생성 후 Map에 저장
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // 이벤트 전송 시, 이벤트 스트림 연결 끊길 시 자동 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 첫 연결 시 503 Service Unavailable 방지용 더미 Event 전송
        sendNotification(emitter, emitterId, "EventStream Created. [userId= " + userId + "]");

        // lastEventId 있다는 것은 비정상적으로 연결이 종료된 것. 그래서 해당 데이터가 남아있는지 체크하고 있다면 남은 데이터를 전송
        if(!lastEventId.isEmpty()){
            Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(userId);
            eventCaches.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendNotification(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    public void send(UserDTO receiver, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.save(NotificationDTO.toEntity(notificationDTO));

        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiver.getId());

        emitters.forEach(
                (id, emitter) -> {
                    emitterRepository.saveEventCache(id, notification);
                    sendNotification(emitter, id, notification);
                }
        );
    }

    private void sendNotification(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
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

    public List<Notification> findAllByUserCode(Long userCode) {
        Sort sort = Sort.by(Sort.Order.asc("is_read"),
                            Sort.Order.asc("priority"),
                            Sort.Order.desc("code"),
                            Sort.Order.desc("sent_date")

        );
        return notificationRepository.findAllByUserCode(userCode,sort);
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
