package ksy.medichat.hospital.service;

import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.dto.HospitalDTO;
import ksy.medichat.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

}
