package ksy.medichat.doctor.dao;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;

import java.util.List;
import java.util.Map;



public interface DoctorMapper {
	//==========의사 회원============
	//("SELECT member_seq.nextval FROM dual")
	public Long selectMem_num();
	//회원가입
	//("INSERT INTO member(mem_num,mem_id,mem_name,mem_auth) VALUES(#{mem_num},#{mem_id},#{mem_name},3)")
	public void insertDoctor(DoctorVO doctor);
	public void insertDoctor_detail(DoctorVO doctor);
	//병원 리스트
	public List<HospitalVO> getHosList(Map<String,Object> map);
	public Integer selectRowCount(Map<String,Object> map);
	public List<HospitalVO> getHosListByKeyword(String keyword);
	//회원상세정보
	//("SELECT * FROM member m JOIN doctor_detail d ON m.mem_num=d.doc_num WHERE mem_num=#{mem_num}")
	public DoctorVO selectDoctor(Long mem_num);
	//회원 목록
	//("SELECT * FROM member m JOIN doctor_detail d ON m.mem_num=d.doc_num WHERE doc_agree=0")
	public List<DoctorVO> docList(Map<String, Object> map);
	//("SELECT * FROM member m JOIN doctor_detail d ON m.mem_num=d.doc_num WHERE hos_num=#{hos_num}")
	public List<DoctorVO> docListByHosNum(long hos_num);
	//회원정보 수정
	//("UPDATE member SET mem_name=#{mem_name} WHERE mem_num=#{mem_num}")
	public void updateDoctor(DoctorVO doctor);
	public void updateDoctor_detail(DoctorVO doctor);
	//비밀번호 수정
	//("UPDATE doctor_detail SET doc_passwd=#{doc_passwd} WHERE doc_num=#{mem_num}")
	public void updateDocPasswd(DoctorVO doctor);
	//프로필 사진 저장
	//("UPDATE member SET mem_photo=#{mem_photo},mem_photoname=#{mem_photoname} WHERE mem_num=#{mem_num}")
	public void uploadDocProfile(DoctorVO doctor);
	//회원탈퇴
	//("UPDATE member SET mem_auth=0 WHERE mem_num=#{mem_num}")
	public void deleteDoctor(Long doc_num);
	//("DELETE FROM doctor_detail WHERE doc_num=#{mem_num}")
	public void deleteDoctor_detail(DoctorVO doctor);
	
	//아이디 중복확인
	public DoctorVO checkId(String mem_id);
	//이메일 확인
	//("SELECT * FROM member m JOIN doctor_detail d ON m.mem_num=d.doc_num WHERE doc_email=#{doc_email} AND mem_name=#{mem_name}")
	public DoctorVO checkEmailAndName(String doc_email,String mem_name);
	//아이디 찾기
	//("SELECT m.mem_id FROM member m JOIN doctor_detail d ON m.mem_num=d.doc_num WHERE mem_name=#{mem_name} AND doc_email=#{doc_email}")
	public DoctorVO findId(DoctorVO doctor);
	//비밀번호 찾기
	//("UPDATE doctor_detail SET doc_passwd=#{doc_passwd} WHERE doc_num=#{mem_num}")
	public void findPasswd(DoctorVO doctor);
	
	//비대면 신청
	//("UPDATE doctor_detail SET doc_treat=1, doc_stime=#{doc_stime}, doc_etime=#{doc_etime}, doc_off=#{doc_off} WHERE doc_num=#{mem_num}")
	public void updateDoctorTreat(DoctorVO doctor);
	
	
	//==========관리자============
	//의사회원가입신청 관리자 승인
	//("UPDATE doctor_detail SET doc_agree=1 WHERE doc_num=#{doc_num}")
	public void updateAgree(DoctorVO doctor);
}
