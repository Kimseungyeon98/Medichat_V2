package ksy.medichat.drug.service;

import ksy.medichat.drug.dao.DrugInfoMapper;
import ksy.medichat.drug.vo.DrugInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DrugServiceImpl implements DrugService{

	@Autowired
	DrugInfoMapper drugInfoMapper;
	
	@Override
	public List<DrugInfoVO> selectList(Map<String, Object> map) {
		return drugInfoMapper.selectList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return drugInfoMapper.selectRowCount(map);
	}

	@Override
	public DrugInfoVO selectDrug(Long drg_num) {
		return drugInfoMapper.selectDrug(drg_num);
	}
}
