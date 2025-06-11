package ksy.medichat.disease.repository;

import ksy.medichat.disease.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease,String> {

}
