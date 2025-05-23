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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 정보 저장 완료!")
    @Transactional
    void saveMember() throws Exception {
        // Given
        // Member 생성
        Member member = Member.builder()
                .memId("testuser")
                .memName("홍길동")
                .memAuth(1)
                .memPhoto(null)
                .memPhotoName(null)
                .build();

        // MemberDetail 생성 (Member를 참조)
        MemberDetail detail = MemberDetail.builder()
                .memAuId("au123")
                .memPasswd("securePassword")
                .memBirth("19950101")
                .memEmail("test@example.com")
                .memPhone("01012345678")
                .memZipcode("12345")
                .memAddress1("서울시 강남구")
                .memAddress2("역삼동")
                .memReg(Date.valueOf(LocalDate.now()))
                .memModify(Date.valueOf(LocalDate.now()))
                .build();

        member.setMemberDetail(detail);
        detail.setMember(member);

        // When
        Member saveMember = memberRepository.save(member);
        Member savedMember = memberRepository.findAll().get(0);

        // Then
        Assertions.assertThat(saveMember).isEqualTo(savedMember);
        System.out.println(savedMember.getMemberDetail().getMemEmail());

        System.out.println("Test 종료!");
    }
}