package ksy.medichat.doctor.service;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;

import java.util.List;
import java.util.Map;


public interface DoctorService {
	//==========의사 회원============
	//회원가입
	public void insertDoctor(DoctorVO doctor);
	//병원 목록
	public List<HospitalVO> getHosList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public List<HospitalVO> getHosListByKeyword(String keyword);
	//회원상세정보
	public DoctorVO selectDoctor(Long mem_num);
	//회원 목록
	public List<DoctorVO> docList(Map<String, Object> map);
	public List<DoctorVO> docListByHosNum(long hos_num);
	//회원정보 수정
	public void updateDoctor(DoctorVO doctor);
	//비밀번호 수정
	public void updateDocPasswd(DoctorVO doctor);
	//프로필 사진 수정
	public void uploadDocProfile(DoctorVO doctor);
	//회원탈퇴
	public void deleteDoctor(Long doc_num);
	public void deleteDoctor_detail(DoctorVO doctor);
	
	public void updateDoctorTreat(DoctorVO doctor);

	//아이디 중복확인
	public DoctorVO checkId(String mem_id);
	//이메일 확인
	public DoctorVO checkEmailAndName(String doc_email,String mem_name);
	//아이디 찾기
	public DoctorVO findId(DoctorVO doctor);
	//비밀번호 찾기
	public void findPasswd(DoctorVO doctor);
	
	//==========관리자============
	public void updateAgree(DoctorVO doctor);
}
