package ksy.medichat.hospital.repository;

import ksy.medichat.hospital.domain.HospitalDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
}
