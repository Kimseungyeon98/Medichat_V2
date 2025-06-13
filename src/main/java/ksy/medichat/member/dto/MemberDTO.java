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

    private Long code;
    @Pattern(regexp = "^[A-Za-z0-9-]{4,}$")
    private String id;
    @NotBlank
    private String name;
    private Integer role;
    private byte[] photo;
    private String photoTitle;
    private String auId;
    @Pattern(regexp = "^[A-Za-z0-9]{4,12}$")
    private String password;
    @NotEmpty
    private String birth;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @Size(min = 5, max = 5)
    private String zipcode;
    @NotBlank
    private String address;
    @NotEmpty
    private String addressDetail;
    private Date registerDate;
    private Date modifyDate;

    private Integer reservationCount;

    // 비밀번호 변경 시
    @Pattern(regexp = "^[A-Za-z0-9]{4,12}$")
    private String nowPassword;
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String captchaChars;
    private String memAuto;


    // Entity (Member + MemberDetail) → DTO 변환
    public static MemberDTO toDTO(Member member) {
        MemberDetail detail = member.getMemberDetail();
        return MemberDTO.builder().code(member.getCode()).id(member.getId()).name(member.getName()).role(member.getRole()).photo(member.getPhoto()).photoTitle(member.getPhotoTitle()).auId(detail.getAuId()).password(detail.getPassword()).birth(detail.getBirth()).email(detail.getEmail()).phone(detail.getPhone()).zipcode(detail.getZipcode()).address(detail.getAddress()).addressDetail(detail.getAddressDetail()).registerDate(detail.getRegisterDate()).modifyDate(detail.getModifyDate()).build();
    }

    // DTO → Entity (Member + MemberDetail) 변환
    public static Member toEntity(MemberDTO dto) {
        Member member = Member.builder().code(dto.getCode()).id(dto.getId()).name(dto.getName()).role(dto.getRole()).photo(dto.getPhoto()).photoTitle(dto.getPhotoTitle()).build();
        MemberDetail detail = MemberDetail.builder().code(dto.getCode()).member(member).auId(dto.getAuId()).password(dto.getPassword()).birth(dto.getBirth()).email(dto.getEmail()).phone(dto.getPhone()).zipcode(dto.getZipcode()).address(dto.getAddress()).addressDetail(dto.getAddressDetail()).registerDate(dto.getRegisterDate()).modifyDate(dto.getModifyDate()).build();
        member.setMemberDetail(detail);
        return member;
    }

    // 비밀번호 일치 여부 체크
    public boolean checkPasswd(String userPasswd) {
        return role > 1 && password.equals(userPasswd);
    }
    // 아이디 찾기 (이메일 일치 여부 체크)
    public boolean checkEmail(String userEmail) {
        return role > 1 && email.equals(userEmail);
    }
    // 아이디 찾기 (이름 일치 여부 체크)
    public boolean checkName(String userName) {
        return role > 1 && name.equals(userName);
    }
    // BLOB 파일 처리
    public void setUpload(MultipartFile upload) throws IOException {
        // MultipartFile -> byte[]
        setPhoto(upload.getBytes());
        // 파일 이름
        setPhotoTitle(upload.getOriginalFilename());
    }
}
