package ksy.medichat.disease.service;

import ksy.medichat.disease.domain.Disease;
import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.disease.repository.DiseaseRepository;
import ksy.medichat.filter.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            if (!diseaseRepository.existsById(disease.getCode())) {
                toSave.add(disease);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        diseaseRepository.saveAll(toSave);
    }

    public boolean existsByNameContaining(String name){
        return diseaseRepository.existsByNameContaining(name);
    }

    public List<DiseaseDTO> getDiseases(Pageable pageable, Search search) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
        return diseaseRepository.findByNameContaining(search.getKeyword(),pageable).stream().map(DiseaseDTO::toDTO).collect(Collectors.toList());
    }

    public List<DiseaseDTO> getRandomDiseaseList(int maxLength, int limit){
        return diseaseRepository.findRandomDiseases(maxLength,limit).stream().map(DiseaseDTO::toDTO).collect(Collectors.toList());
    }

    public List<DiseaseDTO> getTopDiseaseList(int maxLength, int limit){
        return diseaseRepository.findTopDiseases(maxLength, limit).stream().map(DiseaseDTO::toDTO).collect(Collectors.toList());
    }
}
