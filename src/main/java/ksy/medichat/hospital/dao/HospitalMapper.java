package ksy.medichat.hospital.dao;

import ksy.medichat.hospital.vo.HospitalVO;

import java.util.List;
import java.util.Map;


public interface HospitalMapper {
	
	// DB 관리용
	public void insertHospital(HospitalVO hospitalVO); //XML
	public void updateHospital(HospitalVO hospitalVO);
	
	
	// 실 사용
	public List<HospitalVO> selectListHospital(Map<String,Object> map); //XML
	
	public HospitalVO selectHospital(Long hos_num);
	public void updateHitHospital(Long hos_num);
	
	public void initHitHospital();
}
