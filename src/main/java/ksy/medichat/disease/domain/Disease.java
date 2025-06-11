package ksy.medichat.disease.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Disease {
    @Id
    @Column(name = "disease_code")
    private String diseaseCode;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "disease_symptoms")
    private String diseaseSymptoms;

    @Column(name = "disease_department")
    private String diseaseDepartment;
}
