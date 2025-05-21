
package ksy.medichat.health.service;

import ksy.medichat.health.dao.HealthMapper;
import ksy.medichat.health.vo.HealthyBlogVO;
import ksy.medichat.health.vo.HealthyFavVO;
import ksy.medichat.health.vo.HealthyReFavVO;
import ksy.medichat.health.vo.HealthyReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HealthyServiceImpl implements HealthyService{
	
	
	@Autowired
	private HealthMapper mapper;
	@Override
	public void insertHeal(HealthyBlogVO vo) {
		// TODO Auto-generated method stub
		mapper.insertHeal(vo);
	}

	@Override
	public void updateHeal(HealthyBlogVO vo) {
		// TODO Auto-generated method stub
		mapper.updateHeal(vo);
	}

	@Override
	public void deleteHeal(Long healthy_num) {
		// TODO Auto-generated method stub
		
		//댓글 좋아요삭제
		List<Long>list =mapper.selectDeleteByHBlog(healthy_num);
		for(int i=0; i<list.size(); i++) {
			mapper.deleteHreFavByHeal(list.get(i));
		}
		//댓글 삭제
		mapper.deleteHReByHeal(healthy_num);
		mapper.deleteHFavByHeal(healthy_num);
		mapper.deleteHeal(healthy_num);
	}

	@Override
	public HealthyBlogVO getHealthy(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.getHealthy(map);
	}

	@Override
	public Integer selectHealCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectHealCount(map);
	}

	@Override
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectHealList(map);
	}

	@Override
	public void updateHealHit(Long healthy_num) {
		// TODO Auto-generated method stub
		mapper.updateHealHit(healthy_num);
		
	}

	@Override
	public void insertHFav(HealthyFavVO vo) {
		// TODO Auto-generated method stub
		mapper.insertHFav(vo);
	}

	@Override
	public void deleteHFav(HealthyFavVO vo) {
		// TODO Auto-generated method stub
		mapper.deleteHFav(vo);
	}

	@Override
	public Integer selectHFavCount(Long healthy_num) {
		// TODO Auto-generated method stub
		return mapper.selectHFavCount(healthy_num);
	}

	@Override
	public HealthyFavVO selectHFav(HealthyFavVO vo) {
		// TODO Auto-generated method stub
		return mapper.selectHFav(vo);
	}

	@Override
	public void insertHre(HealthyReplyVO vo) {
		// TODO Auto-generated method stub
		mapper.insertHre(vo);
	}

	@Override
	public void updateHre(HealthyReplyVO vo) {
		// TODO Auto-generated method stub
		mapper.updateHre(vo);
		
	}

	@Override
	public void deleteHre(Long hre_num) {
		// TODO Auto-generated method stub
		//댓글좋아요 삭제
		mapper.deleteHreFavByHre(hre_num);
		mapper.deleteHre(hre_num);
	}

	@Override
	public HealthyReplyVO selectHre(Long hre_num) {
		// TODO Auto-generated method stub
		return mapper.selectHre(hre_num);
	}

	@Override
	public List<HealthyReplyVO> selectHreList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectHreList(map);
	}

	@Override
	public Integer selectHreCount(Long healthy_num) {
		// TODO Auto-generated method stub
		return mapper.selectHreCount(healthy_num);
	}

	@Override
	public void insertHreFav(HealthyReFavVO vo) {
		// TODO Auto-generated method stub
		mapper.insertHreFav(vo);
	}

	@Override
	public void deleteHreFav(HealthyReFavVO vo) {
		// TODO Auto-generated method stub
		mapper.deleteHreFav(vo);
	}

	@Override
	public Integer selectHreFavCount(Long hre_num) {
		// TODO Auto-generated method stub
		return mapper.selectHreFavCount(hre_num);
	}

	@Override
	public HealthyReFavVO selectHreFav(HealthyReFavVO vo) {
		// TODO Auto-generated method stub
		return mapper.selectHreFav(vo);
	}

	@Override
	public List<HealthyReplyVO> selectReHreList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectReHreList(map);
	}

	@Override
	public Integer selectMyFavHealCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMyFavHealCount(map);
	}

	@Override
	public List<HealthyBlogVO> selectMyFavHealList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMyFavHealList(map);
	}

	@Override
	public Integer selectMyFavHealReCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMyFavHealReCount(map);
	}

	@Override
	public List<HealthyReplyVO> selectMyFavReHealList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMyFavReHealList(map);
	}

	@Override
	public Integer selectMyHealReCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMyHealReCount(map);
	}

	@Override
	public List<HealthyReplyVO> selectMyReHealList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectMyReHealList(map);
	}

	@Override
	public Integer selectDocByHealCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectDocByHealCount(map);
	}

	@Override
	public List<HealthyBlogVO> selectDocByHealList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectDocByHealList(map);
	}
	


}
