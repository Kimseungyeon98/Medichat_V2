package ksy.medichat.hospital.service;

import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Transactional
    public void initDB(List<Hospital> hospitals) {
        for (Hospital hospital : hospitals) {
            if (!hospitalRepository.existsById(hospital.getHosNum())) {
                hospitalRepository.save(hospital);
            }
        }
    }

    public boolean isEmpty(){
        return hospitalRepository.count() == 0;
    }
}
