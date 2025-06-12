package ksy.medichat.disease.repository;

import ksy.medichat.disease.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease,String> {
    @Query(value = "SELECT * FROM disease WHERE LENGTH(disease_name)<:maxLength ORDER BY random() LIMIT :limit", nativeQuery = true)
    List<Disease> findRandomDiseases(@Param("maxLength") int maxLength, @Param("limit") int limit);

    @Query(value = "SELECT * FROM disease WHERE LENGTH(disease_name)<:maxLength ORDER BY disease_hit LIMIT :limit", nativeQuery = true)
    List<Disease> findTopDiseases(@Param("maxLength") int maxLength, @Param("limit") int limit);
}
