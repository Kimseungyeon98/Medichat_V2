package ksy.medichat.drug.dao;

import ksy.medichat.drug.vo.DrugInfoVO;

import java.util.List;
import java.util.Map;


public interface DrugInfoMapper {
	public void insertDrugInfo(DrugInfoVO drugInfoVO);
	public List<DrugInfoVO> selectList(Map<String, Object> map);
	public Integer selectRowCount(Map<String, Object> map);
	//("SELECT * FROM drug WHERE drg_num=#{drg_num}")
	public DrugInfoVO selectDrug(Long drg_num);
}
