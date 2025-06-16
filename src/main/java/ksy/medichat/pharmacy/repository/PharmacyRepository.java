package ksy.medichat.pharmacy.repository;

import ksy.medichat.pharmacy.domain.Pharmacy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends CrudRepository<Pharmacy, Long> {
}
