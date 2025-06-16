package ksy.medichat.pharmacy.service;

import ksy.medichat.hospital.domain.Hospital;
import ksy.medichat.pharmacy.domain.Pharmacy;
import ksy.medichat.pharmacy.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
}
