package ksy.medichat.member.dao;

import ksy.medichat.consulting.vo.ConsultingVO;
import ksy.medichat.member.vo.MemberVO;

import java.util.List;
import java.util.Map;

;
 

public interface MemberMapper {
	//==========일반 회원============
	//("SELECT member_seq.nextval FROM dual")
	public Long selectMem_num();
	//회원가입
	//("INSERT INTO member(mem_num,mem_id,mem_name) VALUES(#{mem_num},#{mem_id},#{mem_name})")
	public void insertMember(MemberVO member);
	public void insertMember_detail(MemberVO member);
	//회원목록
	public List<MemberVO> getMemList(Map<String,Object> map);
	public Integer selectRowCount(Map<String, Object> map);
	//회원상세정보
	//("SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_num=#{mem_num}")
	public MemberVO selectMember(Long mem_num);
	//회원정보수정
	//("UPDATE member SET mem_name=#{mem_name} WHERE mem_num=#{mem_num}")
	public void updateMember(MemberVO member);
	public void updateMember_detail(MemberVO member);
	//비밀번호 수정
	//("UPDATE member_detail SET mem_passwd=#{mem_passwd} WHERE mem_num=#{mem_num}")
	public void updatePasswd(MemberVO member);
	//프로필 사진 수정
	//("UPDATE member SET mem_photo=#{mem_photo},mem_photoname=#{mem_photoname} WHERE mem_num=#{mem_num}")
	public void updateProfile(MemberVO member);
	//회원탈퇴
	//("UPDATE member SET mem_auth=0 WHERE mem_num=#{mem_num}")
	public void deleteMember(Long mem_num);
	//("DELETE FROM member_detail WHERE mem_num=#{mem_num}")
	public void deleteMember_detail(MemberVO member);
	
	//나의 의료상담 목록
	public List<ConsultingVO> consultList(Map<String,Object> map);
	
	//자동 로그인
	//("UPDATE member_detail SET mem_au_id=#{mem_au_id} WHERE mem_num=#{mem_num}")
	public void updateMem_au_id(String au_id,Long mem_num);
	//("SELECT m.mem_num,m.mem_id,m.mem_auth,d.mem_au_id,d.passwd,m.mem_name,d.mem_email FROM member m JOIN member_detail d ON m.mem_num=d.mem_num WHERE mem_num=#{mem_num}")
	public MemberVO selectMem_au_id(String au_id);
	//("UPDATE member_detail SET mem_au_id='' WHERE mem_num=#{mem_num}")
	public void deleteMem_au_id(Long mem_num);
	
	//아이디 중복확인
	public MemberVO checkId(String mem_id);
	//이메일,이름 확인
	//("SELECT * FROM member JOIN member_detail USING(mem_num) WHERE mem_email=#{mem_email} AND mem_name=#{mem_name}")
	public MemberVO checkEmailAndName(String mem_email,String mem_name);
	//아이디 찾기
	//("SELECT m.mem_id FROM member m JOIN member_detail d ON m.mem_num=d.mem_num WHERE mem_name=#{mem_name} AND mem_email=#{mem_email}")
	public MemberVO findId(MemberVO member);
	//비밀번호 찾기
	//("UPDATE member_detail SET mem_passwd=#{mem_passwd} WHERE mem_num=#{mem_num}")
	public void findPasswd(MemberVO member);
	
	//카카오 로그인
	//("SELECT * FROM member WHERE mem_id=#{mem_id}")
	public MemberVO checkUser(String mem_id);
	//==========관리자============
	//회원등급수정
	//("UPDATE member SET mem_auth=1 WHERE mem_num=#{mem_num}")
	public void updateAuth(MemberVO member);
	//("UPDATE member SET mem_auth=2 WHERE mem_num=#{mem_num}")
	public void cancelAuth(MemberVO member);
	
}
