package ksy.medichat.disease.service;

import ksy.medichat.disease.domain.Disease;
import ksy.medichat.disease.repository.DiseaseRepository;
import ksy.medichat.drug.domain.Drug;
import ksy.medichat.drug.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseService {
    @Autowired
    private DiseaseRepository diseaseRepository;

    public boolean isEmpty(){
        return diseaseRepository.count() == 0;
    }

    @Transactional
    public void initDB(List<Disease> diseases) {
        List<Disease> toSave = new ArrayList<>();
        for (Disease disease : diseases) {
            if (!diseaseRepository.existsById(disease.getDiseaseCode())) {
                toSave.add(disease);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        diseaseRepository.saveAll(toSave);
    }
}
