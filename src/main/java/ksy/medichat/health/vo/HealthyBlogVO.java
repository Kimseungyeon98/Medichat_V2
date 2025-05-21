
package ksy.medichat.health.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter
@Setter
@ToString
public class HealthyBlogVO {
	private long healthy_num;
	private long mem_num;
	private String healthy_title;
	private String healthy_content;
	private Date h_reg_date;
	private Date h_modify_date;
	private int healthy_hit;
	private String h_filename;
	private MultipartFile upload;
	
	private String id;
	private int fav_cnt;
	private int click_num;
	private int re_cnt;
}

