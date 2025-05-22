package ksy.medichat.doctor.domain;

import jakarta.persistence.*;
import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.member.domain.Member;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "doctor_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_num")
    private long docNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_num", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hos_num")
    private Hospital hospital;

    @Column(name = "doc_passwd")
    private String docPasswd;

    @Column(name = "doc_email")
    private String docEmail;

    @Column(name = "doc_reg")
    private Date docReg;

    @Column(name = "doc_license")
    private String docLicense; // 의사 면허증

    @Column(name = "doc_history")
    private String docHistory;

    @Column(name = "doc_treat")
    private int docTreat;

    @Column(name = "doc_off")
    private String docOff; // 휴무요일

    @Column(name = "doc_time")
    private String docTime; // 업무시간

    @Column(name = "doc_stime")
    private String docStime; // 근무시작시간

    @Column(name = "doc_etime")
    private String docEtime; // 근무종료시간

    @Column(name = "doc_agree")
    private int docAgree;
}