package ksy.medichat.community.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ksy.medichat.util.DurationFromNow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CommunityVO {
	private long cbo_num;
	private long mem_num;
	private long cbo_type;
	@NotBlank
	private String cbo_title;
	@NotEmpty
	private String cbo_content;
	private int cbo_hit;
	private String cbo_rdate;
	private String cbo_mdate;
	private int cbo_report;		//누적 신고수
	
	//JOIN을 통해 사용
	private String mem_id;
	
	private int re_cnt;			//댓글 개수
	private int fav_cnt;			//좋아요 개수
	
	public void setCbo_rdate(String cbo_rdate) {
		this.cbo_rdate = 
			    DurationFromNow.getTimeDiffLabel(cbo_rdate);
	}
	public void setCbo_mdate(String cbo_mdate) {
		this.cbo_mdate = 
				DurationFromNow.getTimeDiffLabel(cbo_mdate);
	}
}
