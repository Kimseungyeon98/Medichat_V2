package ksy.medichat.drug.domain;

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
public class Drug {

    @Id
    @Column(name = "drug_num")
    private Long drugNum; // 일련번호

    @Column(name = "drug_name")
    private String drugName; //의약품명

    @Column(name = "drug_company")
    private String drugCompany; //업체명

    @Column(name = "drug_effect", length = 1500)
    private String drugEffect; //효능

    @Column(name = "drug_dosage", length = 1500)
    private String drugDosage; //복용량

    @Column(name = "drug_warning", length = 1500)
    private String drugWarning; //경고

    @Column(name = "drug_precaution", length = 1500)
    private String drugPrecaution; //주의사항

    @Column(name = "drug_interaction", length = 1500)
    private String drugInteraction; //상호작용

    @Column(name = "drug_sideEffect", length = 2500)
    private String drugSideEffect; //부작용

    @Column(name = "drug_storage")
    private String drugStorage; //보관법

    @Column(name = "drug_image")
    private String drugImageLink; //이미지

}
