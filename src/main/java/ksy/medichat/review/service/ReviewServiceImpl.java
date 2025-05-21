package ksy.medichat.review.service;

import ksy.medichat.review.dao.ReviewMapper;
import ksy.medichat.review.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReviewServiceImpl  implements ReviewService{

	@Autowired
	private ReviewMapper mapper;
	@Override
	public void insertReview(ReviewVO vo) {
		// TODO Auto-generated method stub
		mapper.insertReview(vo);
	}

	@Override
	public void updateReview(ReviewVO vo) {
		// TODO Auto-generated method stub
		mapper.updateReview(vo);
	}

	@Override
	public void deleteReview(Long rev_num) {
		// TODO Auto-generated method stub
		mapper.deleteReview(rev_num);
	}

	@Override
	public Integer selectCountByHos(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectCountByHos(map);
	}

	@Override
	public List<ReviewVO> selectReviewListByHos(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReviewListByHos(map);
	}

	@Override
	public Integer selectCountByMem(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectCountByMem(map);
	}

	@Override
	public List<ReviewVO> selectReviewListByMem(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReviewListByMem(map);
	}

	@Override
	public Integer selectCountByDoc(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectCountByDoc(map);
	}

	@Override
	public List<ReviewVO> selectReviewListByDoc(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReviewListByDoc(map);
	}

	@Override
	public ReviewVO selectReviewDetail(Long rev_num) {
		// TODO Auto-generated method stub
		return mapper.selectReviewDetail(rev_num);
	}

	@Override
	public void clickHitReview(Long rev_num) {
		// TODO Auto-generated method stub
		mapper.clickHitReview(rev_num);
	}

}
