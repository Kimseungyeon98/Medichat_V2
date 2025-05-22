package ksy.medichat.doctor.dto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private long docNum;
    private long memNum;
    private String memId;
    private String memName;
    private byte[] memPhoto; // 프로필 사진
    private String memPhotoname; // 프로필 사진명
    private int memAuth; // 권한 등급
    private Long hosNum; // 병원 번호
    private String hosName;
    private String docPasswd;
    private String docEmail;
    private String docLicense; // 의사 면허증
    private String docHistory;
    private int docTreat;
    private String docOff; // 휴무요일
    private String docTime; // 업무시간
    private String docStime; // 근무시작시간
    private String docEtime; // 근무종료시간
    private int docAgree;
    private int reservationCount; // 예약 수
    private String nowPasswd; // 현재 비밀번호
    private String captchaChars; // 캡차 문자
}
