package ksy.medichat.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table
@Getter
@Setter
@ToString(exclude = {"member"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {

    @Id
    private Long code;

    @OneToOne(fetch=FetchType.LAZY)
    @MapsId // member.code가 member 객체의 PK(code)와 동일함을 지정
    @JoinColumn(name = "code")
    private Member member;

    private String auId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(length = 5)
    private String zipcode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @CreationTimestamp
    @Column(updatable = false)
    private Date registerDate;

    private Date modifyDate;
}
