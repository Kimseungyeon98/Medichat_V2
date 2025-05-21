package ksy.medichat.drug.service;

import ksy.medichat.drug.vo.DrugInfoVO;

import java.util.List;
import java.util.Map;

public interface DrugService {
	public List<DrugInfoVO> selectList(Map<String, Object> map);
	public Integer selectRowCount(Map<String, Object> map);
	public DrugInfoVO selectDrug(Long drg_num);
}
