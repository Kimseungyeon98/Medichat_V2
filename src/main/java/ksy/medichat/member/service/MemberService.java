package ksy.medichat.member.service;

import ksy.medichat.consulting.vo.ConsultingVO;
import ksy.medichat.member.vo.MemberVO;

import java.util.List;
import java.util.Map;

;

public interface MemberService {
	//==========일반 회원============
	//회원가입
	public void insertMember(MemberVO member);
	//회원목록
	public List<MemberVO> getMemList(Map<String,Object> map);
	public Integer selectRowCount(Map<String, Object> map);
	//회원상세정보
	public MemberVO selectMember(Long mem_num);
	//회원정보수정
	public void updateMember(MemberVO member);
	//비밀번호 수정
	public void updatePasswd(MemberVO member);
	//프로필 사진 수정
	public void updateProfile(MemberVO member);
	//회원탈퇴
	public void deleteMember(Long mem_num);
	public void deleteMember_detail(MemberVO member);
	
	//나의 의료상담 목록
	public List<ConsultingVO> consultList(Map<String, Object> map);

	//자동 로그인
	public void updateMem_au_id(String mem_au_id,Long mem_num);
	public MemberVO selectMem_au_id(String mem_au_id);
	public void deleteMem_au_id(Long mem_num);

	//아이디 중복확인
	public MemberVO checkId(String mem_id);
	//이메일 확인
	public MemberVO checkEmailAndName(String mem_email,String mem_name);
	//아이디 찾기
	public MemberVO findId(MemberVO member);
	//비밀번호 찾기
	public void findPasswd(MemberVO member);

	//카카오 로그인 회원 체크
	public MemberVO checkUser(String mem_id);

	//==========관리자============
	//회원등급수정
	public void updateAuth(MemberVO member);
	public void cancelAuth(MemberVO member);
}
