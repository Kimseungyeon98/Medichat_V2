package ksy.medichat.pharmacy.controller;

import ksy.medichat.pharmacy.crawling.PharmacyCrawling;
import ksy.medichat.pharmacy.service.PharmacyService;
import ksy.medichat.pharmacy.vo.PharmacyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ManagePhamacyDBController {
	@Autowired
	private PharmacyCrawling pc;
	@Autowired
	private PharmacyService pharmacyService;
	
	@Value("${schedule.use}")
    private boolean useSchedule;
	
	
	@GetMapping("/ksy/insertPharmacyToDB/3002")
	private void initialDBSetUp(){
		pc.main();
		for(PharmacyVO pharmacyVO : pc.list) {
			pharmacyService.insertPharmacy(pharmacyVO);
		}
		System.out.println(pc.list.size());
	}
	
	/* 매 3일 오전 12시에 약국 조회수 초기화 */
	@Scheduled(cron = "${schedule.cron}")
    public void scheduledTask() {
		try {
            if (useSchedule) {
            	pharmacyService.initHitPharmacy();
            	log.debug("<< Batch 시스템 >> : " + "완료");
            }
        } catch (Exception e) {
            log.debug("<<Batch 시스템 >> : " + "오류 발생 Message: {}", e.getMessage());
        }
    }
}