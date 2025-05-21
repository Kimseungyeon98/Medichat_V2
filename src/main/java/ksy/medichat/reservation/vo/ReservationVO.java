package ksy.medichat.reservation.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class ReservationVO {
	private long res_num;
	private long mem_num;
	private long doc_num;
	private long res_status;
	private long res_type;
	private String res_date;
	private String res_time;
	private Date res_reg;
	private String res_content;
	private String doc_name;
	private String hos_name;
	private String patient_name;
	private long hos_num;
}
