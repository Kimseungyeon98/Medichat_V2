package ksy.medichat.drug.service;

import ksy.medichat.drug.domain.Drug;
import ksy.medichat.drug.repository.DrugRepository;
import ksy.medichat.hospital.domain.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public boolean isEmpty(){
        return drugRepository.count() == 0;
    }

    @Transactional
    public void initDB(List<Drug> drugs) {
        List<Drug> toSave = new ArrayList<>();
        for (Drug drug : drugs) {
            if (!drugRepository.existsById(drug.getDrugNum())) {
                toSave.add(drug);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        drugRepository.saveAll(toSave);
    }
}
