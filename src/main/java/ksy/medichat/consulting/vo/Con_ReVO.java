package ksy.medichat.consulting.vo;

import ksy.medichat.doctor.domain.Doctor;
import ksy.medichat.hospital.domain.Hospital;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class Con_ReVO {
	private Long con_re_num;
	private Long con_num;
	private Long doc_num;
	private String con_re_content;
	private String con_re_rDate;
	private String con_re_mDate;
	private int con_re_status;
	
	private Doctor doctor;
	private Hospital hospital;
}