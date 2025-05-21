package ksy.medichat.schedule.dao;

import ksy.medichat.holiday.vo.HolidayVO;

import java.util.List;
import java.util.Map;


public interface ScheduleMapper {
    // 정기휴무요일 가져오기
    //("SELECT doc_off FROM doctor_detail WHERE doc_num=#{doc_num}")
    public String getRegularDayoff(Long doc_num);

    // 근무시작시간, 근무종료시간 가져오기
    //("SELECT doc_stime, doc_etime FROM doctor_detail WHERE doc_num=#{doc_num}")
    public Map<String, String> getWorkingHours(Long doc_num);

    // 개별휴무일 가져오기
    //("SELECT * FROM holiday WHERE doc_num=#{doc_num}")
    public List<HolidayVO> getHoliday(Long doc_num);

    // 개별휴무일 등록
    //("INSERT INTO holiday (holi_num, doc_num, holi_date, holi_time, holi_status) VALUES (holi_seq.nextval, #{doc_num}, #{holi_date}, #{holi_time}, #{holi_status})")
    public void insertHoliday(HolidayVO holiday);

    // 개별휴무일 업데이트
    //("UPDATE holiday SET holi_status = #{holi_status} WHERE doc_num = #{doc_num} AND holi_date = #{holi_date} AND holi_time = #{holi_time}")
    public void updateHoliday(HolidayVO holiday);

    // 개별휴무일 존재 여부 확인
    //("SELECT COUNT(*) FROM holiday WHERE doc_num = #{doc_num} AND holi_date = #{holi_date} AND holi_time = #{holi_time}")
    public int countHoliday(HolidayVO holiday);
    
}
