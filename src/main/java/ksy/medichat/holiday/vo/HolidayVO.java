package ksy.medichat.holiday.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HolidayVO {
	private long holi_num;
	private long doc_num;
	private String holi_date;
	private String holi_time;
	private int holi_status;
}
