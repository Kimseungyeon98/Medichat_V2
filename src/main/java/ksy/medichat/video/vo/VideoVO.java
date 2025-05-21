package ksy.medichat.video.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class VideoVO {
	 private long video_num;
	 private long mem_num;
	 private String video_title;
	 private String video_content;
	 private Date v_reg_date;
	 private Date v_modify_date;
	 private int video_hit;
	 private String v_category;
	 
	 private String mem_id;
}
