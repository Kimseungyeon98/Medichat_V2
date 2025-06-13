package ksy.medichat.hospital.controller;

import ksy.medichat.filter.Date;
import ksy.medichat.filter.Filter;
import ksy.medichat.filter.Location;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.service.HospitalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@Transactional
public class HospitalTest {
    @Autowired
    private HospitalService hospitalService;

    @Test
    @DisplayName("병원 리스트 처리 시간 보기")
    void 병원_리스트_조회_처리속도 (){
        //given
        Pageable pageable = PageRequest.of(0, 10);
        Search search = new Search();
        Location location = new Location(37.5073808,126.900341);
        LocalDateTime now = LocalDateTime.now();
        Date date = new Date(now.getDayOfWeek().getValue(),now.format(DateTimeFormatter.ofPattern("HHmm")));

        search.setLocation(location);
        search.setDate(date);
        // when
        long startTime = System.nanoTime(); // 시작 시간
        List<HospitalDTO> hospitals = hospitalService.findHospitals(pageable, search);   // 측정 대상 메소드
        long endTime = System.nanoTime();   // 끝 시간

        for(HospitalDTO hospital : hospitals){
            System.out.println(hospital.toString());
        }
        // then
        long duration = endTime - startTime;
        System.out.println("selectHospital() 실행 시간: " + duration / 1_000_000 + " ms");
    }
}
