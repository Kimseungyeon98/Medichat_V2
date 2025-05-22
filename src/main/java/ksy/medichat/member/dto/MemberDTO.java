package ksy.medichat.member.dto;

import jakarta.validation.constraints.*;
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
