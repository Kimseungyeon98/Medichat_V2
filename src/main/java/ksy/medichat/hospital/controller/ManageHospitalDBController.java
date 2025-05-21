package ksy.medichat.hospital.controller;

import ksy.medichat.hospital.crawling.HospitalsCrawling;
import ksy.medichat.hospital.service.HospitalService;
import ksy.medichat.hospital.vo.HospitalVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ManageHospitalDBController {
	@Autowired
	private HospitalsCrawling hc;
	@Autowired
	private HospitalService hospitalService;
	
	@Value("${schedule.use}")
    private boolean useSchedule;
	
	
	@GetMapping("/ksy/insertHospitalToDB/3002")
	private void initialDBSetUp() throws Exception{
		hc.main();
		for(HospitalVO hospitalVO : hc.list) {
			hospitalService.insertHospital(hospitalVO);
		}
		System.out.println(hc.list.size());
	}
	
	/* 매 3일 오전 12시에 병원 조회수 초기화 */
	@Scheduled(cron = "${schedule.cron}")
    public void scheduledTask() {
		try {
            if (useSchedule) {
            	hospitalService.initHitHospital();
            	log.debug("<< Batch 시스템 >> : " + "완료");
            }
        } catch (Exception e) {
            log.debug("<<Batch 시스템 >> : " + "오류 발생 Message: {}", e.getMessage());
        }
    }
}