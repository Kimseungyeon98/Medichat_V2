package ksy.medichat.hospital.repository;

import ksy.medichat.filter.Search;
import ksy.medichat.hospital.dto.HospitalDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HospitalRepositoryCustom {
    List<HospitalDTO> findByFilter(Pageable pageable, Search search);
}
