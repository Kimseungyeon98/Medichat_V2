package ksy.medichat.hospital.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "hospital_department")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalDepartment {
    @Id
    @Column(name = "hos_dpt_name")
    private String departmentName;

    @Column(name = "hos_dpt_description")
    private String departmentDescription;

    @Column(name = "hos_dpt_enName")
    private String departmentEnName;
}
