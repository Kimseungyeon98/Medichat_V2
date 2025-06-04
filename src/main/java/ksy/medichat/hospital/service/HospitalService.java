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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    /*public List<HospitalDTO> findHospitals(Pageable pageable, Filter filter) {
        return hospitalRepository.findByFilter(pageable,filter);
    }*/

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
        int fromIndex = page * size;

        double userLat = filter.getUser_lat();
        double userLon = filter.getUser_lon();


        HospitalDTO tmpHospital;

        for(Hospital hospital : hospitalList){

            tmpHospital = HospitalDTO.toDTO(hospital);

            // 0.04는 filter.around 값으로 조정
            if(tmpHospital.getHosLat()==null || tmpHospital.getHosLon()==null ||
                    tmpHospital.getHosLat() < userLat - 0.04 ||
                    tmpHospital.getHosLat() > userLat + 0.04 ||
                    tmpHospital.getHosLon() < userLon - 0.04 ||
                    tmpHospital.getHosLon() > userLon + 0.04){
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
        hospitalDTOList.sort((o1, o2) -> Double.compare(o2.getAround(),o1.getAround()));

        int toIndex = Math.min(fromIndex + size, hospitalDTOList.size());

        if (fromIndex >= hospitalDTOList.size()) {
            return List.of(); // 빈 페이지
        }

        return hospitalDTOList.subList(fromIndex, toIndex);
    }
}
