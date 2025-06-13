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
    private String name;
    @Column(length=500)
    private String description;
    private String enName;

}
