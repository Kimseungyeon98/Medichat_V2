package ksy.medichat.health.vo;

import ksy.medichat.util.DurationFromNow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HealthyReplyVO {
	private long hre_num;
	private  long healthy_num;
	private long mem_num;
	private long hre_renum;
	private String hre_content;
	private String hre_reg_date;
	private String hre_modify_date;
	private int hre_level;
	private String id;
	private int refav_cnt;
	private long click_num;
    private String re_id;
    private String healthy_title;
	//대댓글 갯수
	private int rereply_cnt;
    
	public void setHre_reg_date(String  hre_reg_date) {
		this.hre_reg_date = DurationFromNow.getTimeDiffLabel(hre_reg_date);
	}
	public void setHre_modify_date(String  hre_modify_date) {
		this.hre_modify_date = DurationFromNow.getTimeDiffLabel(hre_modify_date);
	}
}
