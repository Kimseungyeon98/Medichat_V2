package ksy.medichat.hospital.repository;

import ksy.medichat.filter.Filter;
import ksy.medichat.hospital.domain.Hospital;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HospitalRepositoryCustom {
    List<Hospital> findByFilter(Pageable pageable, Filter filter);
}
