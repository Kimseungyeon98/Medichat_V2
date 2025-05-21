package ksy.medichat.schedule.service;

import ksy.medichat.holiday.vo.HolidayVO;
import ksy.medichat.schedule.dao.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private ScheduleMapper scheduleMapper;

	@Override
	public String getRegularDayoff(Long doc_num) {
		return scheduleMapper.getRegularDayoff(doc_num);
	}

	@Override
	public Map<String,String> getWorkingHours(Long doc_num) {
		return scheduleMapper.getWorkingHours(doc_num);
	}

	@Override
	public List<HolidayVO> getHoliday(Long doc_num) {
		return scheduleMapper.getHoliday(doc_num);
	}

	@Override
	public void insertHoliday(HolidayVO holiday) {
		scheduleMapper.insertHoliday(holiday);
	}

	@Override
	public void updateHoliday(HolidayVO holiday) {
		scheduleMapper.updateHoliday(holiday);
	}

	@Override
    public int countHoliday(HolidayVO holiday) {
        return scheduleMapper.countHoliday(holiday);
    }

}
