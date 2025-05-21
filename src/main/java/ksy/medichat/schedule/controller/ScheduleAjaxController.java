package ksy.medichat.schedule.controller;

import ksy.medichat.doctor.vo.DoctorVO;
import ksy.medichat.holiday.vo.HolidayVO;
import ksy.medichat.reservation.service.ReservationService;
import ksy.medichat.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ScheduleAjaxController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/schedule/workingTimes")
    @ResponseBody
    public Map<String, Object> getWorkingTimes(Long doc_num, String res_date, String res_time, HttpSession session) {
        DoctorVO user = (DoctorVO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        if (user == null) {
            map.put("result", "logout");
        } else if (user.getMem_auth() != 3 || user.getDoc_treat() == 0) {
            map.put("result", "wrongAccess");
        } else {
        	Map<String, Object> mapJson = new HashMap<String, Object>();
        	mapJson.put("doc_num", doc_num);
        	mapJson.put("res_date", res_date);
        	List<String> reservedTimes = reservationService.getResExist(mapJson);
            Map<String, String> workingHours = scheduleService.getWorkingHours(doc_num);
            map.put("result", "success");
            map.put("workingHours", workingHours);
            map.put("reservedTimes", reservedTimes);
        }
        return map;
    }

    @GetMapping("/schedule/holidays")
    @ResponseBody
    public Map<String, Object> getHolidays(Long doc_num, HttpSession session) {
        DoctorVO user = (DoctorVO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();

        if (user == null) {
            map.put("result", "logout");
        } else if (user.getMem_auth() != 3 || user.getDoc_treat() == 0) {
            map.put("result", "wrongAccess");
        } else {
            List<HolidayVO> holidays = scheduleService.getHoliday(doc_num);
            map.put("result", "success");
            map.put("holidays", holidays);
        }
        return map;
    }

    @PostMapping("/schedule/updateTimes")
    @ResponseBody
    public Map<String, Object> updateTimes(@RequestBody List<HolidayVO> modifiedTimes, HttpSession session) {
        DoctorVO user = (DoctorVO) session.getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();

        if (user == null) {
            map.put("result", "logout");
        } else if (user.getMem_auth() != 3 || user.getDoc_treat() == 0) {
            map.put("result", "wrongAccess");
        } else {
            try {
                for (HolidayVO holiday : modifiedTimes) {
                    holiday.setDoc_num(user.getMem_num());  // 세션에서 사용자 ID 설정
                    int count = scheduleService.countHoliday(holiday);
                    if (count == 0) {
                        scheduleService.insertHoliday(holiday);
                    } else {
                        scheduleService.updateHoliday(holiday);
                    }
                }
                map.put("result", "success");
            } catch (Exception e) {
                map.put("result", "error");
                map.put("message", e.getMessage());
            }
        }
        return map;
    }
}
