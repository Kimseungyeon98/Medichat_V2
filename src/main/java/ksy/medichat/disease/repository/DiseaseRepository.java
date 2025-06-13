package ksy.medichat.disease.repository;

import ksy.medichat.disease.domain.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease,String> {

    Page<Disease> findByNameContaining(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM disease WHERE LENGTH(name)<:maxLength ORDER BY random() LIMIT :limit", nativeQuery = true)
    List<Disease> findRandomDiseases(@Param("maxLength") int maxLength, @Param("limit") int limit);

    @Query(value = "SELECT * FROM disease WHERE LENGTH(name)<:maxLength ORDER BY hit LIMIT :limit", nativeQuery = true)
    List<Disease> findTopDiseases(@Param("maxLength") int maxLength, @Param("limit") int limit);

    boolean existsByNameContaining(String name);
}
