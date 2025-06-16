package ksy.medichat.drug.repository;

import ksy.medichat.drug.domain.Drug;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    public List<Drug> findByNameContaining(String keyword, Pageable pageable);
}
