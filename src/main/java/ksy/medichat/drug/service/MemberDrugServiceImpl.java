package ksy.medichat.drug.service;

import ksy.medichat.drug.dao.MemberDrugMapper;
import ksy.medichat.drug.vo.DrugInfoVO;
import ksy.medichat.drug.vo.MemberDrugVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberDrugServiceImpl implements MemberDrugService{
	
	@Autowired
	MemberDrugMapper memberdrugMapper;
	
	@Override
	public List<DrugInfoVO> selectDrugList(String drg_name) {
		//의약품 검색
		return memberdrugMapper.selectDrugList(drg_name);
	}
	
	@Override
	public List<MemberDrugVO> selectMemberDrugList(Long mem_num) {
		return memberdrugMapper.selectMemberDrugList(mem_num);
	}

	@Override
	public void insertDrug(MemberDrugVO memberDrug) {
		memberdrugMapper.insertDrug(memberDrug);
	}

	@Override
	public MemberDrugVO selectDrug(Long med_num) {
		return memberdrugMapper.selectDrug(med_num);
	}
	
	@Override
	public void updateDrug(MemberDrugVO memberDrug) {
		memberdrugMapper.updateDrug(memberDrug);
	}

	@Override
	public void deleteDrug(Long med_num) {
		memberdrugMapper.deleteDrug(med_num);
	}

}
