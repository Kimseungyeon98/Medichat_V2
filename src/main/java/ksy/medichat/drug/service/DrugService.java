package ksy.medichat.drug.service;

import ksy.medichat.disease.dto.DiseaseDTO;
import ksy.medichat.drug.domain.Drug;
import ksy.medichat.drug.dto.DrugDTO;
import ksy.medichat.drug.repository.DrugRepository;
import ksy.medichat.filter.Search;
import ksy.medichat.hospital.domain.Hospital;
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
            if (!drugRepository.existsById(drug.getCode())) {
                toSave.add(drug);
            }
        }
        // 존재하지 않는 것만 모아서 일괄 저장
        drugRepository.saveAll(toSave);
    }

    public List<DrugDTO> getDrugs(Pageable pageable, Search search) {
        if(search.getKeyword()==null){
            search.setKeyword("");
        }
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
        return drugRepository.findByNameContaining(search.getKeyword(),pageable).stream().map(DrugDTO::toDTO).collect(Collectors.toList());
    }
}
