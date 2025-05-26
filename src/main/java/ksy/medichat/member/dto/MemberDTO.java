package ksy.medichat.member.dto;

import jakarta.validation.constraints.*;
import ksy.medichat.member.domain.Member;
import ksy.medichat.member.domain.MemberDetail;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;

@Getter
@Setter
@ToString(exclude = {"memPhoto"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long memNum;

    @Pattern(regexp = "^[A-Za-z0-9-]{4,}$")
    private String memId;

    @NotBlank
    private String memName;

    private int memAuth;

    private byte[] memPhoto;
    private String memPhotoName;

    private String memAuto;
    private String memAuId;
    @Pattern(regexp = "^[A-Za-z0-9]{4,12}$")
    private String memPasswd;
    @NotEmpty
    private String memBirth;
    @Email
    @NotBlank
    private String memEmail;
    @NotBlank
    private String memPhone;
    @Size(min = 5, max = 5)
    private String memZipcode;
    @NotBlank
    private String memAddress1;
    @NotEmpty
    private String memAddress2;
    private Date memReg;
    private Date memModify;
    private int reservationCount;
    // 비밀번호 변경 시
    @Pattern(regexp = "^[A-Za-z0-9]{4,12}$")
    private String nowPasswd;
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String captchaChars;


    // Entity → DTO 변환
    public static MemberDTO toDTO(Member member) {
        if (member == null || member.getMemberDetail() == null) {return null;}
        MemberDetail detail = member.getMemberDetail();
        return MemberDTO.builder().memNum(member.getMemNum()).memId(member.getMemId()).memName(member.getMemName()).memAuth(member.getMemAuth()).memPhoto(member.getMemPhoto()).memPhotoName(member.getMemPhotoName()).memAuId(detail.getMemAuId()).memPasswd(detail.getMemPasswd()).memBirth(detail.getMemBirth()).memEmail(detail.getMemEmail()).memPhone(detail.getMemPhone()).memZipcode(detail.getMemZipcode()).memAddress1(detail.getMemAddress1()).memAddress2(detail.getMemAddress2()).memReg(detail.getMemRdate()).memModify(detail.getMemMdate()).build();
    }

    // DTO → Entity 변환
    public static Member toEntity(MemberDTO dto) {
        if (dto == null) {return null;}
        Member member = Member.builder().memNum(dto.getMemNum()).memId(dto.getMemId()).memName(dto.getMemName()).memAuth(dto.getMemAuth()).memPhoto(dto.getMemPhoto()).memPhotoName(dto.getMemPhotoName()).build();
        MemberDetail detail = MemberDetail.builder().memNum(dto.getMemNum()).member(member).memAuId(dto.getMemAuId()).memPasswd(dto.getMemPasswd()).memBirth(dto.getMemBirth()).memEmail(dto.getMemEmail()).memPhone(dto.getMemPhone()).memZipcode(dto.getMemZipcode()).memAddress1(dto.getMemAddress1()).memAddress2(dto.getMemAddress2()).memRdate(dto.getMemReg()).memMdate(dto.getMemModify()).build();
        member.setMemberDetail(detail);  // 양방향 설정
        return member;
    }

    // 비밀번호 일치 여부 체크
    public boolean checkPasswd(String userPasswd) {
        return memAuth > 1 && memPasswd.equals(userPasswd);
    }
    // 아이디 찾기 (이메일 일치 여부 체크)
    public boolean checkEmail(String userEmail) {
        return memAuth > 1 && memEmail.equals(userEmail);
    }
    // 아이디 찾기 (이름 일치 여부 체크)
    public boolean checkName(String userName) {
        return memAuth > 1 && memName.equals(userName);
    }
    // BLOB 파일 처리
    public void setUpload(MultipartFile upload) throws IOException {
        // MultipartFile -> byte[]
        setMemPhoto(upload.getBytes());
        // 파일 이름
        setMemPhotoName(upload.getOriginalFilename());
    }
}
