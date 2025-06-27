package ksy.medichat.notification.service;

import jakarta.transaction.Transactional;
import ksy.medichat.notification.domain.Notification;
import ksy.medichat.notification.dto.NotificationDTO;
import ksy.medichat.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification save(NotificationDTO notificationDTO) {
        return notificationRepository.save(NotificationDTO.toEntity(notificationDTO));
    }

    public Notification read(Long code) {
        Notification notification = notificationRepository.findById(code).orElse(null);
        notification.setIsRead(true);
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

}
