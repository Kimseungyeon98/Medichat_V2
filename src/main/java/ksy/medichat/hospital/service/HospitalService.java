package ksy.medichat.hospital.service;

import ksy.medichat.filter.Date;
import ksy.medichat.filter.Location;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.dto.HospitalDepartmentDTO;
import ksy.medichat.hospital.repository.HospitalDepartmentRepository;
import ksy.medichat.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static java.lang.Math.*;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Transactional
    public void initDB(List<Hospital> hospitals) {
        List<Hospital> toSave = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            if (!hospitalRepository.existsById(hospital.getCode())) {
                toSave.add(hospital);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        hospitalRepository.saveAll(toSave);
    }

    public boolean isEmpty(){
        return hospitalRepository.count() == 0;
    }

    public List<HospitalDTO> findHospitals(Pageable pageable, Search search) {
        List<Hospital> hospitalList = hospitalRepository.findAll();
        List<HospitalDTO> hospitalDTOList = new ArrayList<>();

        String keyword = search.getKeyword();
        if(keyword==null){
            keyword = "";
        }
        String commonFilter = search.getCommonFilter();
        if(commonFilter==null){
            commonFilter = "";
        }
        String sortType = search.getSortType();
        if(sortType==null){
            sortType = "NEAR";
        }
        Integer maxDistance = search.getMaxDistance();
        if(maxDistance==null){
            maxDistance = 2000;
        }
        Date date = search.getDate();
        if(date==null){
            date = new Date();
            LocalDateTime now = LocalDateTime.now();
            date.setDay(now.getDayOfWeek().getValue());//1:월 2:화 3:수 4:목 5:금 6:토 7:일
            date.setTime(now.format(DateTimeFormatter.ofPattern("HHmm")));//hh:mm
            search.setDate(date);
        }
        String time = search.getDate().getTime();
        Integer day = search.getDate().getDay();

        Location location = search.getLocation();
        if(location==null){
            location = new Location();
            location.setUserLat(37.498071922037155);
            location.setUserLng(127.02799461568996);
            search.setLocation(location);
        }
        Double userLat = search.getLocation().getUserLat();
        Double userLon = search.getLocation().getUserLng();

        int weekendEndTime = 1300;
        int weekdaysEndTime = 1800;

        int page = pageable.getPageNumber(); // 현재 페이지 (0부터 시작)
        int size = pageable.getPageSize();

        // 서울 기준
        double latitude = 37.5;
        // 위도는 1도 ≈ 111,000m
        double latOffset = maxDistance / 111000.0;
        // 경도는 위도에 따라 다름 (cos 위도 적용)
        double lngOffset = maxDistance / (111000.0 * Math.cos(Math.toRadians(latitude)));

        HospitalDTO tmpHospital;
        for(Hospital hospital : hospitalList){
            tmpHospital = HospitalDTO.toDTO(hospital);
            if(tmpHospital.getLat()==null || tmpHospital.getLng()==null ||
                tmpHospital.getLat() < userLat - latOffset ||
                tmpHospital.getLat() > userLat + latOffset ||
                tmpHospital.getLng() < userLon - lngOffset ||
                tmpHospital.getLng() > userLon + lngOffset) {
                continue;
            }

            if(!keyword.isBlank()){
                if(!tmpHospital.getAddress().contains(keyword) &&
                   !tmpHospital.getName().contains(keyword) &&
                   !tmpHospital.getDescription().contains(keyword)){
                    continue;
                }
            }

            if(!commonFilter.isBlank()){
                if (commonFilter.equals("ING")) {
                    if (day == 1) {
                        if (tmpHospital.getTime1C().equals("null") || Integer.parseInt(tmpHospital.getTime1C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 2) {
                        if (tmpHospital.getTime2C().equals("null") || Integer.parseInt(tmpHospital.getTime2C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 3) {
                        if (tmpHospital.getTime3C().equals("null") || Integer.parseInt(tmpHospital.getTime3C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 4) {
                        if (tmpHospital.getTime4C().equals("null") || Integer.parseInt(tmpHospital.getTime4C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 5) {
                        if (tmpHospital.getTime5C().equals("null") || Integer.parseInt(tmpHospital.getTime5C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 6) {
                        if (tmpHospital.getTime6C().equals("null") || Integer.parseInt(tmpHospital.getTime6C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 7) {
                        if (tmpHospital.getTime7C().equals("null") || Integer.parseInt(tmpHospital.getTime7C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    }
                } else if (commonFilter.equals("NIGHTTIME")) {
                    if (day == 1) {
                        if (tmpHospital.getTime1C().equals("null") || Integer.parseInt(tmpHospital.getTime1C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 2) {
                        if (tmpHospital.getTime2C().equals("null") || Integer.parseInt(tmpHospital.getTime2C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 3) {
                        if (tmpHospital.getTime3C().equals("null") || Integer.parseInt(tmpHospital.getTime3C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 4) {
                        if (tmpHospital.getTime4C().equals("null") || Integer.parseInt(tmpHospital.getTime4C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 5) {
                        if (tmpHospital.getTime5C().equals("null") || Integer.parseInt(tmpHospital.getTime5C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 6) {
                        if (tmpHospital.getTime6C().equals("null") || Integer.parseInt(tmpHospital.getTime6C()) <= weekendEndTime) {
                            continue;
                        }
                    } else if (day == 7) {
                        if (tmpHospital.getTime7C().equals("null") || Integer.parseInt(tmpHospital.getTime7C()) <= weekendEndTime) {
                            continue;
                        }
                    }
                } else if (commonFilter.equals("WEEKEND")) {
                    if (!tmpHospital.getWeekendAt().equals("Y")) {
                        continue;
                    }
                }

            }

            double around = round((6378137 * acos(cos(toRadians(userLat)) * cos(toRadians(tmpHospital.getLat())) * cos(toRadians(tmpHospital.getLng()) - toRadians(userLon)) + sin(toRadians(userLat)) * sin(toRadians(tmpHospital.getLat())))));
            tmpHospital.setAround(around);

            hospitalDTOList.add(tmpHospital);
        }

        //정렬 around 기준
        if(sortType.equals("NEAR")){
            hospitalDTOList.sort(Comparator.comparingDouble(HospitalDTO::getAround));
        }

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, hospitalDTOList.size());

        if (fromIndex >= hospitalDTOList.size()) {
            return List.of(); // 빈 페이지
        }

        return hospitalDTOList.subList(fromIndex, toIndex);
    }

    public HospitalDTO findHospital (String hosNum) {
        HospitalDTO hospital = null;
        Optional<Hospital> optionalHospitalDTO = hospitalRepository.findById(Long.parseLong(hosNum));

        if(optionalHospitalDTO.isPresent()){
            hospital = HospitalDTO.toDTO(optionalHospitalDTO.get());
        }
        return hospital;
    }

    public List<HospitalDepartmentDTO> getHospitalDepartment(){
        return hospitalDepartmentRepository.findAll().stream().map(HospitalDepartmentDTO::toDTO).collect(Collectors.toList());
    }
}
