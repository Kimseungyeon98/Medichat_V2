
package ksy.medichat.health.service;

import ksy.medichat.health.vo.HealthyBlogVO;
import ksy.medichat.health.vo.HealthyFavVO;
import ksy.medichat.health.vo.HealthyReFavVO;
import ksy.medichat.health.vo.HealthyReplyVO;

import java.util.List;
import java.util.Map;

public interface HealthyService {
	public void insertHeal(HealthyBlogVO vo);
	public void updateHeal(HealthyBlogVO vo);
	public void deleteHeal(Long healthy_num);
	public HealthyBlogVO getHealthy(Map<String, Object> map);
	public Integer selectHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectHealList(Map<String, Object> map);
	public void updateHealHit(Long healthy_num);

	//게시글 좋아요
	public void insertHFav(HealthyFavVO vo);
	public void deleteHFav(HealthyFavVO vo);
	public Integer selectHFavCount(Long healthy_num);
	public HealthyFavVO selectHFav(HealthyFavVO vo);

	//댓글

	public void insertHre(HealthyReplyVO vo);
	public void updateHre(HealthyReplyVO vo);
	public void deleteHre(Long hre_num);
	public HealthyReplyVO selectHre(Long hre_num);
	public List<HealthyReplyVO> selectHreList(Map<String,Object> map);
	public Integer selectHreCount(Long healthy_num);

	//댓글 좋아요
	//댓글 좋아요
	public void insertHreFav(HealthyReFavVO vo);
	public void deleteHreFav(HealthyReFavVO vo);
	public Integer selectHreFavCount(Long hre_num);
	public HealthyReFavVO selectHreFav(HealthyReFavVO vo);

	//답글 불러오기
	public List<HealthyReplyVO> selectReHreList(Map<String,Object> map);
	
	//내가 한 좋아요목록
	//좋아요 게시글
	public Integer selectMyFavHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectMyFavHealList(Map<String, Object> map);
	//좋아요 댓글
	public Integer selectMyFavHealReCount(Map<String, Object> map);
	public List<HealthyReplyVO> selectMyFavReHealList(Map<String, Object> map);

	//내가 쓴 댓글 목록
	//댓글
	public Integer selectMyHealReCount(Map<String, Object> map);
	public List<HealthyReplyVO> selectMyReHealList(Map<String, Object> map);
	
	//의사
	public Integer selectDocByHealCount(Map<String, Object> map);
	public List<HealthyBlogVO> selectDocByHealList(Map<String, Object> map);

}
