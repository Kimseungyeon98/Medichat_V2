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

    @Column(name = "disease_name", length = 500)
    private String diseaseName;

    @Column(name = "disease_description", length = 2000)
    private String diseaseDescription;

    @Column(name = "disease_department", length = 500)
    private String diseaseDepartment;

    @Column(name = "disease_hit")
    private Long diseaseHit;
}
