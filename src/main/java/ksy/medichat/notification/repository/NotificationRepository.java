package ksy.medichat.notification.repository;

import ksy.medichat.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserCode(Long userCode);

    Long countByUserCode(Long userCode);

}
