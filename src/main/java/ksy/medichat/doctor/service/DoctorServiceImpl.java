package ksy.medichat.doctor.service;

import ksy.medichat.doctor.dao.DoctorMapper;
import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.hospital.vo.HospitalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	DoctorMapper doctorMapper;
	
	@Override
	public void insertDoctor(DoctorVO doctor) {
		doctor.setMem_num(doctorMapper.selectMem_num());
		doctorMapper.insertDoctor(doctor);
		doctorMapper.insertDoctor_detail(doctor);
	}

	@Override
	public DoctorVO selectDoctor(Long mem_num) {
		return doctorMapper.selectDoctor(mem_num);
	}
	
	@Override
	public List<DoctorVO> docListByHosNum(long hos_num) {
		return doctorMapper.docListByHosNum(hos_num);
	}

	@Override
	public void updateDoctor(DoctorVO doctor) {
		doctorMapper.updateDoctor(doctor);
		doctorMapper.updateDoctor_detail(doctor);
	}

	@Override
	public void updateDocPasswd(DoctorVO doctor) {
		doctorMapper.updateDocPasswd(doctor);
	}

	@Override
	public void uploadDocProfile(DoctorVO doctor) {
		doctorMapper.uploadDocProfile(doctor);
	}

	@Override
	public void deleteDoctor(Long doc_num) {
		doctorMapper.deleteDoctor(doc_num);
	}

	@Override
	public void deleteDoctor_detail(DoctorVO doctor) {
		doctorMapper.deleteDoctor_detail(doctor);
	}
	
	@Override
	public DoctorVO checkId(String mem_id) {
		return doctorMapper.checkId(mem_id);
	}

	@Override
	public DoctorVO findId(DoctorVO doctor) {
		return doctorMapper.findId(doctor);
	}

	@Override
	public void findPasswd(DoctorVO doctor) {
		doctorMapper.findPasswd(doctor);
	}

	@Override
	public List<HospitalVO> getHosList(Map<String,Object> map) {
		return doctorMapper.getHosList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return doctorMapper.selectRowCount(map);
	}

	@Override
	public List<HospitalVO> getHosListByKeyword(String keyword) {
		return doctorMapper.getHosListByKeyword(keyword);
	}

	@Override
	public void updateAgree(DoctorVO doctor) {
		doctorMapper.updateAgree(doctor);
	}

	@Override
	public List<DoctorVO> docList(Map<String, Object> map) {
		return doctorMapper.docList(map);
	}

	@Override
	public void updateDoctorTreat(DoctorVO doctor) {
		doctorMapper.updateDoctorTreat(doctor);
		
	}

	@Override
	public DoctorVO checkEmailAndName(String doc_email,String mem_name) {
		return doctorMapper.checkEmailAndName(doc_email,mem_name);
	}

}
