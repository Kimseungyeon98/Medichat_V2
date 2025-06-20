package ksy.medichat.member.service;

import ksy.medichat.member.domain.Member;
import ksy.medichat.member.domain.MemberDetail;
import ksy.medichat.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@ActiveProfiles("TEST")
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 정보 저장 완료!")
    void 회원_정보_저장(){
        // Given
        // Member 생성
        Member member = Member.builder()
                .id("testuser")
                .name("홍길동")
                .build();

        // MemberDetail 생성 (Member를 참조)
        MemberDetail detail = MemberDetail.builder()
                .auId("au123")
                .password("securePassword")
                .birth("19950101")
                .email("test@example.com")
                .phone("01012345678")
                .zipcode("12345")
                .address("서울시 강남구")
                .addressDetail("역삼동")
                .build();

        member.setMemberDetail(detail);
        detail.setMember(member);

        // When
        Member saveMember = memberRepository.save(member);
        memberRepository.flush();
        Member savedMember = memberRepository.findById(saveMember.getCode()).orElseThrow();

        // Then
        Assertions.assertThat(saveMember).isEqualTo(savedMember);
        System.out.println("member: " + savedMember);
        System.out.println("memberDetail: " + savedMember.getMemberDetail().toString());

        System.out.println("Test 종료!");
    }
}