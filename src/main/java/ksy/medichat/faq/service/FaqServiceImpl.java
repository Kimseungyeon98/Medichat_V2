package ksy.medichat.faq.service;

import ksy.medichat.faq.dao.FaqMapper;
import ksy.medichat.faq.vo.FaqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FaqServiceImpl implements FaqService{
	
	@Autowired
	private FaqMapper mapper;
	@Override
	public void insertF(FaqVO vo) {
		// TODO Auto-generated method stub
		mapper.insertF(vo);
	}

	@Override
	public void updateF(FaqVO vo) {
		// TODO Auto-generated method stub
		mapper.updateF(vo);
	}

	@Override
	public void deleteF(Long faq_num) {
		// TODO Auto-generated method stub
		mapper.deleteF(faq_num);
	}

	@Override
	public Integer selectCountF(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectCountF(map);
	}

	@Override
	public FaqVO selectF(Long faq_num) {
		// TODO Auto-generated method stub
		return mapper.selectF(faq_num);
	}

	@Override
	public List<FaqVO> selectFList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectFList(map);
	}

	@Override
	public void updateFhit(Long faq_num) {
		// TODO Auto-generated method stub
		mapper.updateFhit(faq_num);
	}

}
