package ksy.medichat.member.vo;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;

@Getter
@Setter
@ToString(exclude = {"mem_photo"})
public class MemberVO {
   private long mem_num;
   @Pattern(regexp="^[A-Za-z0-9-]{4,}$")
   private String mem_id;
   @NotBlank
   private String mem_name;
   private byte[] mem_photo;      //프로필 사진
   private String mem_photoname;   //프로필 사진명
   private int mem_auth;         //권한 등급(0:탈퇴,1:정지,2:일반,3:의사,9:관리자)
   private String mem_auto;
   private String mem_au_id;         //자동로그인ID
   @Pattern(regexp="^[A-Za-z0-9]{4,12}$")
   //영문자와 숫자의 조합이며 길이가 4에서 12 사이
   /* @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,12}$") */
   private String mem_passwd;
   @NotEmpty
   private String mem_birth;
   @Email
   @NotBlank
   private String mem_email;
   @NotBlank
   private String mem_phone;
   @Size(min=5,max=5)
   private String mem_zipcode;
   @NotBlank
   private String mem_address1;
   @NotEmpty
   private String mem_address2;
   private Date mem_reg;
   private Date mem_modify;
   private int reservationCount;
   
   //비밀번호 변경시 현재 비밀번호를 저장하는 용도로 사용
   @Pattern(regexp="^[A-Za-z0-9]{4,12}$")
   private String now_passwd;
   
   //비밀번호 변경시에만 조건체크
   @Pattern(regexp="^[A-Za-z0-9]+$")
   private String captcha_chars;
   
   //비밀번호 일치 여부 체크
   public boolean checkPasswd(String userPasswd) {
      if(mem_auth > 1 && mem_passwd.equals(userPasswd)) {
         return true;
      }  
      return false;
   }
   //아이디 찾기 (이메일 일치 여부 체크)
 	public boolean checkEmail(String userEmail) {
 		if(mem_auth>1 && mem_email.equals(userEmail)) {
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
