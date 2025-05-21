package ksy.medichat.community.vo;

import ksy.medichat.util.DurationFromNow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunityReplyVO {
	private long cre_num;
	private long cbo_num;	//게시글 번호
	private long mem_num;	//회원번호
	private String cre_content;
	private String cre_rdate;
	private String cre_mdate;
	private long cre_level;
	private long cre_ref;
	private long cre_report;
	
	private String mem_id;
	
	private int click_num;
	private int refav_cnt;	//댓글 좋아요 개수
	
	private String parent_id;//부모글 아이디
	
	private int reply_cnt;
	
	private String postTitle; // 게시글 제목 추가
	
	public void setCre_rdate(String cre_rdate) {
		this.cre_rdate = 
			    DurationFromNow.getTimeDiffLabel(cre_rdate);
	}
	public void setCre_mdate(String cre_mdate) {
		this.cre_mdate = 
				DurationFromNow.getTimeDiffLabel(cre_mdate);
	}
}
