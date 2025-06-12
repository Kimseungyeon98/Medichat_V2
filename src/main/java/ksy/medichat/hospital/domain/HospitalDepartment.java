package ksy.medichat.hospital.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "hospital_treatement_")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalTreatmentDepartment {
    private String departmentName;
    private String departmentDescription;
    private String departmentEnName;
}
