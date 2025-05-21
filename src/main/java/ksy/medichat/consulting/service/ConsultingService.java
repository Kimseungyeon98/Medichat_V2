package ksy.medichat.consulting.service;

import ksy.medichat.consulting.vo.Con_ReVO;
import ksy.medichat.consulting.vo.ConsultingVO;

import java.util.List;
import java.util.Map;

public interface ConsultingService {
	public void createConsulting(ConsultingVO consulting);
	public List<ConsultingVO> getListConsulting(Map<String,Object> map);
	
	public ConsultingVO getConsulting(Long con_num);
	public void modifyConsulting(ConsultingVO consulting);
	
	public boolean isWriteReply(Map<String,Object> map);
	
	public void removeConsulting(Long con_num);
	
	
	public void createCon_Re(Con_ReVO con_Re);
	public List<Con_ReVO> getListCon_Re(Map<String,Object> map);
	
	public void modifyCon_Re_Status(Map<String,Object> map); 
}
