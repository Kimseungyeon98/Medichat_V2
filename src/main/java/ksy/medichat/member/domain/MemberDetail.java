package ksy.medichat.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "member_detail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {

    @Id
    @Column(name = "mem_num")
    private Long memNum;

    @OneToOne(fetch=FetchType.LAZY)
    @MapsId // mem_num가 member 객체의 PK(memberId)와 동일함을 지정
    @JoinColumn(name = "mem_num")
    private Member member;

    @Column(name = "mem_au_id")
    private String memAuId;

    @Column(name = "mem_passwd", nullable = false)
    private String memPasswd;

    @Column(name = "mem_birth", nullable = false)
    private String memBirth;

    @Column(name = "mem_email", nullable = false)
    private String memEmail;

    @Column(name = "mem_phone", nullable = false)
    private String memPhone;

    @Column(name = "mem_zipcode", length = 5)
    private String memZipcode;

    @Column(name = "mem_address1", nullable = false)
    private String memAddress1;

    @Column(name = "mem_address2", nullable = false)
    private String memAddress2;

    @Column(name = "mem_rdate")
    private Date memRdate;

    @Column(name = "mem_mdate")
    private Date memMdate;
}
