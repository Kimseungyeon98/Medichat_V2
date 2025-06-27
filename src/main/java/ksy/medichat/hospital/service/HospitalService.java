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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public List<HospitalDTO> findHospitals(Search search) {
        List<Hospital> hospitalList = hospitalRepository.findAll();
        List<HospitalDTO> hospitalDTOList = new ArrayList<>();

        // keyword가 null일 경우 초기화
        String keyword = search.getKeyword();
        if(keyword==null){
            keyword = "";
        }
        // commonFilter가 null일 경우 초기화
        String commonFilter = search.getCommonFilter();
        if(commonFilter==null){
            commonFilter = "";
        }
        // sortType이 null일 경우 초기화
        String sortType = search.getSortType();
        if(sortType==null){
            sortType = "NEAR";
        }
        // maxDistance가 null일 경우 초기화
        Integer maxDistance = search.getMaxDistance();
        if(maxDistance==null){
            maxDistance = 2000;
        }
        // date가 null일 경우 초기화
        Date date = search.getDate();
        if(date.getTime()==null || date.getDay()==null){
            date = new Date();
            ZonedDateTime koreaTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDateTime now = koreaTime.toLocalDateTime();
            date.setDay(now.getDayOfWeek().getValue()); // 1:월 ~ 7:일
            date.setTime(now.format(DateTimeFormatter.ofPattern("HHmm"))); // "hh:mm" → "HHmm" (24시간제)
            search.setDate(date);
        }
        String time = search.getDate().getTime();
        Integer day = search.getDate().getDay();

        // location이 null일 경우 초기화
        Location location = search.getLocation();
        if(location==null){
            location = new Location();
            location.setUserLat(37.498071922037155);
            location.setUserLng(127.02799461568996);
            search.setLocation(location);
        }
        Double userLat = search.getLocation().getUserLat();
        Double userLon = search.getLocation().getUserLng();

        // 야간진료 기준 시간 고정
        int weekendEndTime = 1300;
        int weekdaysEndTime = 1800;

        // 페이징 관련
        int page = search.getPageable().getPage(); // 현재 페이지 (0부터 시작)
        int size = search.getPageable().getSize();

        // 거리 계산용
        double latitude = 37.5; // 서울 기준
        double latOffset = maxDistance / 111000.0; // 위도는 1도 ≈ 111,000m
        double lngOffset = maxDistance / (111000.0 * Math.cos(Math.toRadians(latitude))); // 경도는 위도에 따라 다름 (cos 위도 적용)

        // 병원 리스트를 순회하며 조건 처리
        HospitalDTO tmpHospital;
        for(Hospital hospital : hospitalList){
            tmpHospital = HospitalDTO.toDTO(hospital);
            // 위치 정보가 없거나
            if(tmpHospital.getLat()==null || tmpHospital.getLng()==null ||
                //위치 정보가 범위 밖이라면 continue
                tmpHospital.getLat() < userLat - latOffset ||
                tmpHospital.getLat() > userLat + latOffset ||
                tmpHospital.getLng() < userLon - lngOffset ||
                tmpHospital.getLng() > userLon + lngOffset) {
                continue;
            }

            // 검색어가 비어있거나 그 어디에도 포함하지 않는다면 continue
            if(!keyword.isBlank()){
                if(!tmpHospital.getAddress().contains(keyword) &&
                   !tmpHospital.getName().contains(keyword) &&
                   !tmpHospital.getDescription().contains(keyword)){
                    continue;
                }
            }

            // 일반조건이 비어있거나
            if(!commonFilter.isBlank()){
                if (commonFilter.contains("ING") || commonFilter.contains("NIGHTTIME")) {
                    String timeC = switch (day) {
                        case 1 -> tmpHospital.getTime1C();
                        case 2 -> tmpHospital.getTime2C();
                        case 3 -> tmpHospital.getTime3C();
                        case 4 -> tmpHospital.getTime4C();
                        case 5 -> tmpHospital.getTime5C();
                        case 6 -> tmpHospital.getTime6C();
                        case 7 -> tmpHospital.getTime7C();
                        default -> null;
                    };

                    if (timeC == null || timeC.equals("null")) {
                        continue;
                    }

                    int targetTime = commonFilter.contains("ING")
                            ? Integer.parseInt(time)
                            : (day <= 5 ? weekdaysEndTime : weekendEndTime);

                    if (Integer.parseInt(timeC) <= targetTime) {
                        continue;
                    }
                } else if (commonFilter.contains("WEEKEND")) {
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
        }else if(sortType.equals("REVIEW")){

        } else if(sortType.equals("SCORE")){

        } else if(sortType.equals("HIT")){

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
