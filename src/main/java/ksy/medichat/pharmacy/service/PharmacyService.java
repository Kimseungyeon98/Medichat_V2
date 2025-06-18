package ksy.medichat.pharmacy.service;

import ksy.medichat.filter.Date;
import ksy.medichat.filter.Location;
import ksy.medichat.filter.Search;
import ksy.medichat.pharmacy.domain.Pharmacy;
import ksy.medichat.pharmacy.dto.PharmacyDTO;
import ksy.medichat.pharmacy.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.*;

@Service
public class PharmacyService {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Transactional
    public void initDB(List<Pharmacy> pharmacies) {
        List<Pharmacy> toSave = new ArrayList<>();
        for (Pharmacy pharmacy : pharmacies) {
            if (!pharmacyRepository.existsById(pharmacy.getCode())) {
                toSave.add(pharmacy);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        pharmacyRepository.saveAll(toSave);
    }

    public boolean isEmpty(){
        return pharmacyRepository.count() == 0;
    }

    public List<PharmacyDTO> findPharmacies(Pageable pageable, Search search) {

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        List<PharmacyDTO> pharmacyDTOList = new ArrayList<>();

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
            ZonedDateTime koreaTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
            LocalDateTime now = koreaTime.toLocalDateTime();
            date.setDay(now.getDayOfWeek().getValue()); // 1:월 ~ 7:일
            date.setTime(now.format(DateTimeFormatter.ofPattern("HHmm"))); // "hh:mm" → "HHmm" (24시간제)
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

        PharmacyDTO tmpPharmacy;
        for(Pharmacy pharmacy : pharmacyList){
            tmpPharmacy = PharmacyDTO.toDTO(pharmacy);
            if(tmpPharmacy.getLat()==null || tmpPharmacy.getLng()==null ||
                    tmpPharmacy.getLat() < userLat - latOffset ||
                    tmpPharmacy.getLat() > userLat + latOffset ||
                    tmpPharmacy.getLng() < userLon - lngOffset ||
                    tmpPharmacy.getLng() > userLon + lngOffset) {
                continue;
            }

            if(!keyword.isBlank()){
                if(!tmpPharmacy.getAddress().contains(keyword) &&
                        !tmpPharmacy.getName().contains(keyword) &&
                        !tmpPharmacy.getDescription().contains(keyword)){
                    continue;
                }
            }

            if(!commonFilter.isBlank()){
                if (commonFilter.equals("ING")) {
                    if (day == 1) {
                        if (tmpPharmacy.getTime1C().equals("null") || Integer.parseInt(tmpPharmacy.getTime1C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 2) {
                        if (tmpPharmacy.getTime2C().equals("null") || Integer.parseInt(tmpPharmacy.getTime2C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 3) {
                        if (tmpPharmacy.getTime3C().equals("null") || Integer.parseInt(tmpPharmacy.getTime3C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 4) {
                        if (tmpPharmacy.getTime4C().equals("null") || Integer.parseInt(tmpPharmacy.getTime4C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 5) {
                        if (tmpPharmacy.getTime5C().equals("null") || Integer.parseInt(tmpPharmacy.getTime5C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 6) {
                        if (tmpPharmacy.getTime6C().equals("null") || Integer.parseInt(tmpPharmacy.getTime6C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    } else if (day == 7) {
                        if (tmpPharmacy.getTime7C().equals("null") || Integer.parseInt(tmpPharmacy.getTime7C()) <= Integer.parseInt(time)) {
                            continue;
                        }
                    }
                } else if (commonFilter.equals("NIGHTTIME")) {
                    if (day == 1) {
                        if (tmpPharmacy.getTime1C().equals("null") || Integer.parseInt(tmpPharmacy.getTime1C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 2) {
                        if (tmpPharmacy.getTime2C().equals("null") || Integer.parseInt(tmpPharmacy.getTime2C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 3) {
                        if (tmpPharmacy.getTime3C().equals("null") || Integer.parseInt(tmpPharmacy.getTime3C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 4) {
                        if (tmpPharmacy.getTime4C().equals("null") || Integer.parseInt(tmpPharmacy.getTime4C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 5) {
                        if (tmpPharmacy.getTime5C().equals("null") || Integer.parseInt(tmpPharmacy.getTime5C()) <= weekdaysEndTime) {
                            continue;
                        }
                    } else if (day == 6) {
                        if (tmpPharmacy.getTime6C().equals("null") || Integer.parseInt(tmpPharmacy.getTime6C()) <= weekendEndTime) {
                            continue;
                        }
                    } else if (day == 7) {
                        if (tmpPharmacy.getTime7C().equals("null") || Integer.parseInt(tmpPharmacy.getTime7C()) <= weekendEndTime) {
                            continue;
                        }
                    }
                } else if (commonFilter.equals("WEEKEND")) {
                    if (!tmpPharmacy.getWeekendAt().equals("Y")) {
                        continue;
                    }
                }

            }

            double around = round((6378137 * acos(cos(toRadians(userLat)) * cos(toRadians(tmpPharmacy.getLat())) * cos(toRadians(tmpPharmacy.getLng()) - toRadians(userLon)) + sin(toRadians(userLat)) * sin(toRadians(tmpPharmacy.getLat())))));
            tmpPharmacy.setAround(around);

            pharmacyDTOList.add(tmpPharmacy);
        }

        //정렬 around 기준
        if(sortType.equals("NEAR")){
            pharmacyDTOList.sort(Comparator.comparingDouble(PharmacyDTO::getAround));
        }

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, pharmacyDTOList.size());

        if (fromIndex >= pharmacyDTOList.size()) {
            return List.of(); // 빈 페이지
        }

        return pharmacyDTOList.subList(fromIndex, toIndex);
    }
}
