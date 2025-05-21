package ksy.medichat.drug.service;

import ksy.medichat.drug.vo.DrugInfoVO;
import ksy.medichat.drug.vo.MemberDrugVO;

import java.util.List;

public interface MemberDrugService {
	public List<DrugInfoVO> selectDrugList (String drg_name);
	public List<MemberDrugVO> selectMemberDrugList(Long mem_num);
	public void insertDrug(MemberDrugVO memberDrug);
	public MemberDrugVO selectDrug(Long med_num);
	public void updateDrug(MemberDrugVO memberDrug);
	public void deleteDrug(Long med_num);
}
