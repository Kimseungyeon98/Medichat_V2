package ksy.medichat.consulting.dao;

import ksy.medichat.consulting.vo.Con_ReVO;
import ksy.medichat.consulting.vo.ConsultingVO;

import java.util.List;
import java.util.Map;


public interface ConsultingMapper {
	
	/* 게시글 */
	public void insertConsulting(ConsultingVO consulting);
	public ConsultingVO selectConsulting(Long con_num);
	public void updateConsulting(ConsultingVO consulting);
	public void deleteConsulting(Long con_num);
	
	public int selectReplyCntByDocNum(Map<String,Object> map);
	
	public List<ConsultingVO> selectListConsulting(Map<String,Object> map);
	
	
	/* 답글 */
	public void insertCon_Re(Con_ReVO con_Re);
	public List<Con_ReVO> selectListCon_Re(Map<String,Object> map);
	public int selectCon_ReCnt(Long con_num);
	
	public void deleteAllCon_Re(Long con_num);
	
	public void updateCon_Re_Status(Map<String,Object> map);
	
	
}
