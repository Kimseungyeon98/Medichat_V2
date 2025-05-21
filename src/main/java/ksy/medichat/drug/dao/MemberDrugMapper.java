package ksy.medichat.drug.dao;

import ksy.medichat.drug.vo.DrugInfoVO;
import ksy.medichat.drug.vo.MemberDrugVO;

import java.util.List;


public interface MemberDrugMapper {
	//의약품 검색
	//("SELECT drg_name FROM drug WHERE drg_name LIKE '%' || #{drg_name} || '%'")
	public List<DrugInfoVO> selectDrugList (String drg_name);
	//("SELECT * FROM medicine WHERE mem_num=#{mem_num}")
	public List<MemberDrugVO> selectMemberDrugList(Long mem_num);
	public void insertDrug(MemberDrugVO memberDrug);
	//("SELECT * FROM medicine WHERE med_num=#{med_num}")
	public MemberDrugVO selectDrug(Long med_num);//업데이트를 위해 특정 med_num의 레코드를 구함
	public void updateDrug(MemberDrugVO memberDrug);
	//("DELETE FROM medicine WHERE med_num=#{med_num}")
	public void deleteDrug(Long med_num);
}
