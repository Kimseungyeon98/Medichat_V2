package ksy.medichat.faq.service;

import ksy.medichat.faq.vo.FaqVO;

import java.util.List;
import java.util.Map;

public interface FaqService {
	
	public void insertF(FaqVO vo);
	public void updateF(FaqVO vo);
	public void deleteF(Long faq_num);
	public Integer selectCountF(Map<String,Object> map);
	public  FaqVO selectF(Long faq_num);
	public List<FaqVO> selectFList(Map<String,Object> map);
	//조회수
	public void updateFhit(Long faq_num);
}
