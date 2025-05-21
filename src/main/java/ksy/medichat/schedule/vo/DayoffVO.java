package ksy.medichat.schedule.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DayoffVO {
 private long doff_num;
 private long doc_num;
 private String doff_date;
 private String doff_time;
}
