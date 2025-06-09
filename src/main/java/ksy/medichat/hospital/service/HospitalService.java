package ksy.medichat.hospital.service;

import ksy.medichat.filter.Filter;
import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import static java.lang.Math.*;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Transactional
    public void initDB(List<Hospital> hospitals) {
        List<Hospital> toSave = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            if (!hospitalRepository.existsById(hospital.getHosNum())) {
                toSave.add(hospital);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        hospitalRepository.saveAll(toSave);
    }

    public boolean isEmpty(){
        return hospitalRepository.count() == 0;
    }

    public List<HospitalDTO> findHospitals(Pageable pageable, Filter filter) {
        List<Hospital> hospitalList = hospitalRepository.findAll();
        List<HospitalDTO> hospitalDTOList = new ArrayList<>();

        String keyword = filter.getKeyword();
        String commonFilter = filter.getCommonFilter();
        String time = filter.getTime();
        int day = filter.getDay();

        int weekendEndTime = 1300;
        int weekdaysEndTime = 1800;

        int page = pageable.getPageNumber(); // 현재 페이지 (0부터 시작)
        int size = pageable.getPageSize();

        double userLat = filter.getUser_lat();
        double userLon = filter.getUser_lon();

        // 서울 기준
        double latitude = 37.5;
        // 위도는 1도 ≈ 111,000m
        double latOffset = filter.getAround() / 111000.0;
        // 경도는 위도에 따라 다름 (cos 위도 적용)
        double lonOffset = filter.getAround() / (111000.0 * Math.cos(Math.toRadians(latitude)));

        HospitalDTO tmpHospital;

        for(Hospital hospital : hospitalList){
            tmpHospital = HospitalDTO.toDTO(hospital);
            if(tmpHospital.getHosLat()==null || tmpHospital.getHosLon()==null ||
                tmpHospital.getHosLat() < userLat - latOffset ||
                tmpHospital.getHosLat() > userLat + latOffset ||
                tmpHospital.getHosLon() < userLon - lonOffset ||
                tmpHospital.getHosLon() > userLon + lonOffset) {
                continue;
            }

            if(keyword!=null && !keyword.isBlank()){
                if(!tmpHospital.getHosAddr().contains(keyword) &&
                   !tmpHospital.getHosName().contains(keyword) &&
                   !tmpHospital.getHosInfo().contains(keyword)){
                    continue;
                }
            }

            if(commonFilter!=null && !commonFilter.isBlank()){
                if (commonFilter.equals("ING")) {
                    if (day == 1) {
                        if (tmpHospital.getHosTime1C().equals("null") || Integer.parseInt(tmpHospital.getHosTime1C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 2) {
                        if (tmpHospital.getHosTime2C().equals("null") || Integer.parseInt(tmpHospital.getHosTime2C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 3) {
                        if (tmpHospital.getHosTime3C().equals("null") || Integer.parseInt(tmpHospital.getHosTime3C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 4) {
                        if (tmpHospital.getHosTime4C().equals("null") || Integer.parseInt(tmpHospital.getHosTime4C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 5) {
                        if (tmpHospital.getHosTime5C().equals("null") || Integer.parseInt(tmpHospital.getHosTime5C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 6) {
                        if (tmpHospital.getHosTime6C().equals("null") || Integer.parseInt(tmpHospital.getHosTime6C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 7) {
                        if (tmpHospital.getHosTime7C().equals("null") || Integer.parseInt(tmpHospital.getHosTime7C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    }
                } else if (commonFilter.equals("NIGHTTIME")) {
                    if (day == 1) {
                        if (tmpHospital.getHosTime1C().equals("null") || Integer.parseInt(tmpHospital.getHosTime1C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 2) {
                        if (tmpHospital.getHosTime2C().equals("null") || Integer.parseInt(tmpHospital.getHosTime2C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 3) {
                        if (tmpHospital.getHosTime3C().equals("null") || Integer.parseInt(tmpHospital.getHosTime3C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 4) {
                        if (tmpHospital.getHosTime4C().equals("null") || Integer.parseInt(tmpHospital.getHosTime4C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 5) {
                        if (tmpHospital.getHosTime5C().equals("null") || Integer.parseInt(tmpHospital.getHosTime5C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 6) {
                        if (tmpHospital.getHosTime6C().equals("null") || Integer.parseInt(tmpHospital.getHosTime6C()) <= weekendEndTime) {
                            continue;
                        }
                    } else if (day == 7) {
                        if (tmpHospital.getHosTime7C().equals("null") || Integer.parseInt(tmpHospital.getHosTime7C()) <= weekendEndTime) {
                            continue;
                        }
                    }
                } else if (commonFilter.equals("WEEKEND")) {
                    if (!tmpHospital.getHosWeekendAt().equals("Y")) {
                        continue;
                    }
                }

            }

            double around = round((6378137 * acos(cos(toRadians(userLat)) * cos(toRadians(tmpHospital.getHosLat())) * cos(toRadians(tmpHospital.getHosLon()) - toRadians(userLon)) + sin(toRadians(userLat)) * sin(toRadians(tmpHospital.getHosLat())))));
            tmpHospital.setAround(around);

            hospitalDTOList.add(tmpHospital);
        }

        //정렬 around 기준
        if(filter.getSortType().equals("NEAR")){
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
}
