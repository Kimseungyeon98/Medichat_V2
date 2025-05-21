package ksy.medichat.community.service;

import ksy.medichat.community.dao.CommunityMapper;
import ksy.medichat.community.vo.CommunityReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommunityServiceImpl implements CommunityService{
	
	@Autowired CommunityMapper communityMapper;
	
	/*-----------------------------게시판글-----------------------------*/
	@Override
	public List<CommunityVO> selectCommunityList(Map<String, Object> map) {
		return communityMapper.selectCommunityList(map);
	}

	@Override
	public Integer selectRowCount(Map<String, Object> map) {
		return communityMapper.selectRowCount(map);
	}

	@Override
	public void insertCommunity(CommunityVO community) {
		communityMapper.insertCommunity(community);
	}

	@Override
	public CommunityVO selectCommunity(Long cbo_num) {
		return communityMapper.selectCommunity(cbo_num);
	}

	@Override
	public void updateCommunity(CommunityVO community) {
		communityMapper.updateCommunity(community);
	}

	@Override
	public void deleteCommunity(Long cbo_num) {//게시판글 삭제
		//댓글(답글) 삭제
		//댓글(답글) 좋아요 삭제
		//게시판 좋아요 삭제
		communityMapper.deleteFavByCommunityNum(cbo_num);
		//글 삭제
		communityMapper.deleteCommunity(cbo_num);
	}

	@Override
	public void updateHit(Long cbo_num) {
		communityMapper.updateHit(cbo_num);
	}
	
	public List<CommunityReplyVO> selectCommentsByUser(long userNum) {
	    return communityMapper.selectCommentsByUser(userNum);
	}

	
	/*-----------------------------게시판글 좋아요-----------------------------*/
	@Override
	public CommunityFavVO selectFav(CommunityFavVO fav) {
		return communityMapper.selectFav(fav);
	}

	@Override
	public Integer selectFavCount(Long cbo_num) {
		return communityMapper.selectFavCount(cbo_num);
	}

	@Override
	public void insertFav(CommunityFavVO fav) {
		communityMapper.insertFav(fav);
	}

	@Override
	public void deleteFav(CommunityFavVO fav) {
		communityMapper.deleteFav(fav);
	}

	/*-----------------------------게시판글 댓글-----------------------------*/
	
	@Override
	public List<CommunityReplyVO> selectListCommentAndReply(Map<String, Object> map) {
		return communityMapper.selectListCommentAndReply(map);
	}
	
	@Override
	public Integer selectRowCountCommentAndReply(Map<String, Object> map) {
		return communityMapper.selectRowCountCommentAndReply(map);
	}
	

	@Override
	public List<CommunityReplyVO> selectListComment(Map<String, Object> map) {
		return communityMapper.selectListComment(map);
	}
	
	@Override
	public CommunityReplyVO selectComment(Long cre_num) {
		return communityMapper.selectComment(cre_num);
	}

	@Override
	public Integer selectCountComment(Long cre_num) {
		return communityMapper.selectCountComment(cre_num);
	}

	@Override
	public void insertComment(CommunityReplyVO communityReply) {
		communityMapper.insertComment(communityReply);
	}

	@Override
	public void updateComment(CommunityReplyVO communityReply) {
		communityMapper.updateComment(communityReply);
	}

	@Override
	public void deleteComment(Long cre_num) {
		communityMapper.deleteComment(cre_num);
		//댓글 좋아요 삭제
	}
	
	/*-----------------------------게시판글 대댓글-----------------------------*/
	@Override
	public List<CommunityReplyVO> selectListReply(Long cre_num) {
		return communityMapper.selectListReply(cre_num);
	}
	
	@Override
	public CommunityReplyVO selectReply(Long cre_num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectCountReply(Long cre_num) {
		return communityMapper.selectCountReply(cre_num);
	}
	
	@Override
	public void insertReply(CommunityReplyVO communityReply) {
		communityMapper.insertReply(communityReply);
	}

	/*-----------------------------댓글 좋아요-----------------------------*/
}
