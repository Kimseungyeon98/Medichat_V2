package ksy.medichat.community.service;

import ksy.medichat.community.vo.CommunityReplyVO;

import java.util.List;
import java.util.Map;

public interface CommunityService {
	/*-----------------------------게시판글-----------------------------*/
	//게시판
	public List<CommunityVO> selectCommunityList (Map<String, Object> map);//목록
	public Integer selectRowCount(Map<String, Object> map);//개수
	public void insertCommunity(CommunityVO community);//등록
	public CommunityVO selectCommunity(Long cbo_num); //상세
	public void updateCommunity(CommunityVO community);//수정
	public void deleteCommunity(Long cbo_num);//삭제

	//조회수
	public void updateHit(Long cbo_num);

	//게시판 좋아요
	public CommunityFavVO selectFav(CommunityFavVO fav); //좋아요 목록..?
	public Integer selectFavCount(Long cbo_num); //좋아요 개수
	public void insertFav(CommunityFavVO fav);	//좋아요 처리
	public void deleteFav(CommunityFavVO fav); //좋아요 삭제

	/*-----------------------------댓글-----------------------------*/
	//댓글 및 대댓글 조회
	public List<CommunityReplyVO> selectListCommentAndReply(Map<String, Object> map); //댓글 및 답글 총 목록
	public Integer selectRowCountCommentAndReply(Map<String, Object> map); //댓글 및 답글 총 개수
	
	public List<CommunityReplyVO> selectCommentsByUser(long userNum);
	
	//댓글
	public List<CommunityReplyVO> selectListComment(Map<String, Object> map);
	public CommunityReplyVO selectComment(Long cre_num); //댓글개수
	public Integer selectCountComment(Long cre_num);
	public void insertComment(CommunityReplyVO communityReply);
	public void updateComment(CommunityReplyVO communityReply);
	public void deleteComment(Long cre_num);
	
	//대댓글
	public List<CommunityReplyVO> selectListReply(Long cre_num);
	public CommunityReplyVO selectReply(Long cre_num);
	public Integer selectCountReply(Long cre_num);
	public void insertReply(CommunityReplyVO communityReply);
	
	/*-----------------------------답글(대댓글)-----------------------------*/
}
