package ksy.medichat.user.service;

import ksy.medichat.user.domain.User;
import ksy.medichat.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@ActiveProfiles("TEST")
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 정보 저장 완료!")
    void 회원_정보_저장(){
        // Given
        // User 생성
        User user = User.builder()
                .id("testuser")
                .password("1234")
                .build();

        // When
        User saveUser = userRepository.save(user); // 저장
        userRepository.flush();
        User savedUser = userRepository.findById(saveUser.getCode()).orElseThrow(); // 저장된 데이터 조회

        // Then
        Assertions.assertThat(saveUser).isEqualTo(savedUser); // 저장한 데이터와 저장된 데이터 비교

        System.out.println("Test 종료!");
    }
}