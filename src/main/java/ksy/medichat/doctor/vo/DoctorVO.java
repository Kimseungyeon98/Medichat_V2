package ksy.medichat.doctor.vo;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;

@Getter
@Setter
@ToString(exclude = {"mem_photo"})
public class DoctorVO {
	private long mem_num;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String mem_id;
	@NotBlank
	private String mem_name;
	private byte[] mem_photo;		//프로필 사진
	private String mem_photoname;	//프로필 사진명
	private int mem_auth;			//권한 등급(0:탈퇴,1:정지,2:일반,3:의사,9:관리자)
	private long doc_num;
	@NotNull(message = "병원을 선택하세요.")
	private Long hos_num;
	private String hos_name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String doc_passwd;
	@Email
	@NotBlank
	private String doc_email;
	private Date doc_reg;
	@NotEmptyFile(message = "의사 면허증은 필수 첨부")
	private MultipartFile doc_upload;

	private String doc_license;		//의사 면허증

	private String doc_history;
	private int doc_treat;
	private String doc_off;			//휴무요일
	private String doc_time;		//업무시간
	private String doc_stime;		//근무시작시간
	private String doc_etime;		//근무종료시간
	private int doc_agree;
	private int reservationCount;
	
//	@NotBlank(message = "비밀번호를 입력해주세요.")
//  private String password; // 사용자가 입력한 비밀번호
	
	//비밀번호 변경시 현재 비밀번호를 저장하는 용도로 사용
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String now_passwd;
	
	//비밀번호 변경시에만 조건체크
	@Pattern(regexp="^[A-Za-z0-9]+$")
	private String captcha_chars;
	
	//비밀번호 일치 여부 체크
	public boolean checkPasswd(String userPasswd) {
		if(mem_auth > 1 && doc_passwd.equals(userPasswd)) {
			return true;
		}
		return false;
	}
  
	//관리자 승인 여부 체크
	public boolean checkAgree(int adminAgree) {
		if(doc_agree == 1) {
			return true;
		}
		return false;
	}
	//아이디 찾기 (이메일 일치 여부 체크)
	public boolean checkEmail(String userEmail) {
		if(mem_auth>1 && doc_email.equals(userEmail)) {
			return true;
		}
		return false;
	}
	//아이디 찾기 (이메일 일치 여부 체크)
	public boolean checkName(String userName) {
		if(mem_auth>1 && mem_name.equals(userName)) {
			return true;
		}
		return false;
	}
	//이미지 BLOB 처리
	public void setUpload(MultipartFile upload)throws IOException {
		//MultipartFile > byte[]
		setMem_photo(upload.getBytes());
		//파일 이름
		setMem_photoname(upload.getOriginalFilename());
	}
}