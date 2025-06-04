package ksy.medichat.hospital.controller;

import ksy.medichat.filter.Filter;
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
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("HHmm")); //hh:mm
        int day = now.getDayOfWeek().getValue(); //1:월 2:화 3:수 4:목 5:금 6:토 7:일
        Filter filter = Filter.builder()
                .keyword("서울")
                .sortType("NEAR")
                .commonFilter("")
                .time(time)
                .day(day)
                .user_lat(37.5073808)
                .user_lon(126.900341)
                .build();
        // when
        long startTime = System.nanoTime(); // 시작 시간
        List<HospitalDTO> hospitals = hospitalService.findHospitalsTest(pageable,filter);   // 측정 대상 메소드
        long endTime = System.nanoTime();   // 끝 시간

        for(HospitalDTO hospital : hospitals){
            System.out.println(hospital.toString());
        }
        // then
        long duration = endTime - startTime;
        System.out.println("selectHospital() 실행 시간: " + duration / 1_000_000 + " ms");
    }
}
