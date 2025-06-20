package ksy.medichat.user.repository;

import ksy.medichat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsById(String id);

    Optional<User> findById(String id);

}
