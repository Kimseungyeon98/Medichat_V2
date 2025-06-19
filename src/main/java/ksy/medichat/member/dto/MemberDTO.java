package ksy.medichat.member.dto;

import jakarta.validation.constraints.Pattern;
import ksy.medichat.member.domain.Member;
import ksy.medichat.member.domain.MemberDetail;
import lombok.*;

@Getter
@Setter
@ToString(exclude = {"photo"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long code;
    @Pattern(regexp = "^[A-Za-z0-9-]{4,}$")
    private String id;
    @Pattern(regexp = "^[A-Za-z0-9]{4,12}$")
    private String password;
    /*@NotBlank
    private String name;
    private Integer role;
    private byte[] photo;
    private String photoTitle;
    private String auId;
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
    private String memAuto;*/


    // Entity (Member + MemberDetail) → DTO 변환
    public static MemberDTO toDTO(Member member) {
        MemberDetail detail = member.getMemberDetail();
        return MemberDTO.builder().code(member.getCode()).id(member.getId()).password(member.getMemberDetail().getPassword()).build();
    }

    // DTO → Entity (Member + MemberDetail) 변환
    public static Member toEntity(MemberDTO dto) {
        Member member = Member.builder().code(dto.getCode()).id(dto.getId()).build();
        MemberDetail detail = MemberDetail.builder().code(dto.getCode()).password(dto.getPassword()).member(member).build();
        member.setMemberDetail(detail);
        return member;
    }

    /*// 비밀번호 일치 여부 체크
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
    }*/
}
