package ksy.medichat.user.repository;

import ksy.medichat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<User, Long>{
    boolean existsById(String id);
    /*// selectMem_num -> 다음 시퀀스 조회하는 메서드

    // insertMember , insertMember_detail -> Data Jpa save로 대체

    @Query("SELECT m FROM Member m JOIN FETCH m.memberDetail d WHERE (:keyword IS NULL OR m.memName LIKE %:keyword%)")
    List<Member> findMemList(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(m) FROM Member m WHERE (:memName IS NULL OR m.memName = :memName)")
    Integer countMemberByMemName(String memName);

    // 회원 상세 조회 (JOIN)
    @Query("SELECT m FROM Member m JOIN FETCH m.memberDetail WHERE m.memNum = :memNum")
    Optional<Member> findMemberWithDetail(@Param("memNum") Long memNum);

    // 아이디 중복 확인
    @Query("SELECT m FROM Member m JOIN FETCH m.memberDetail d WHERE m.memId = :memId")
    Optional<Member> findByMemId(@Param("memId") String memId);

    // 이메일 & 이름으로 회원 조회
    @Query("SELECT m FROM Member m JOIN FETCH m.memberDetail d WHERE d.memEmail = :email AND m.memName = :name")
    Optional<Member> findByEmailAndName(@Param("email") String email, @Param("name") String name);

    // 아이디 찾기
    @Query("SELECT m.memId FROM Member m JOIN m.memberDetail d WHERE m.memName = :name AND d.memEmail = :email")
    Optional<String> findIdByNameAndEmail(@Param("name") String name, @Param("email") String email);

    // 카카오 로그인 확인
    @Query("SELECT m FROM Member m WHERE m.memId = :memId")
    Optional<Member> checkKakaoUser(@Param("memId") String memId);

    // 자동 로그인 정보로 조회
    @Query("SELECT m FROM Member m JOIN FETCH m.memberDetail d WHERE d.memAuId = :auId")
    Optional<Member> findByMemAuId(@Param("auId") String auId);

    // 회원등급 수정
    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.memAuth = :auth WHERE m.memNum = :memNum")
    void updateMemAuth(@Param("memNum") Long memNum, @Param("auth") int auth);

    // 회원 탈퇴 (auth 0으로 변경)
    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.memAuth = 0 WHERE m.memNum = :memNum")
    void softDeleteMember(@Param("memNum") Long memNum);*/
}
