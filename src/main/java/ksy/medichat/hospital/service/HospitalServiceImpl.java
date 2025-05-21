package ksy.medichat.hospital.service;

import ksy.medichat.hospital.dao.HospitalMapper;
import ksy.medichat.hospital.vo.HospitalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService{
	@Autowired
	private HospitalMapper hospitalMapper;

	@Override
	public void insertHospital(HospitalVO hospitalVO) {
		hospitalMapper.insertHospital(hospitalVO);
	}

	@Override
	public void updateHospital(HospitalVO hospitalVO) {
		
	}

	@Override
	public List<HospitalVO> selectListHospital(Map<String,Object> map) {
		return hospitalMapper.selectListHospital(map);
	}
	
	@Override
	public HospitalVO selectHospital(Long hos_num) {
		//병원 조회수
		hospitalMapper.updateHitHospital(hos_num);
		
		return hospitalMapper.selectHospital(hos_num);
	}
	
	@Override
	public void initHitHospital() {
		hospitalMapper.initHitHospital();
	}
}
