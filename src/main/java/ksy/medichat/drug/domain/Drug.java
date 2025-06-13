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
    private Long code; // 일련번호

    private String name; //의약품명

    private String company; //업체명

    @Column(length = 1500)
    private String effect; //효능

    @Column(length = 1500)
    private String dosage; //복용량

    @Column(length = 1500)
    private String warning; //경고

    @Column(length = 1500)
    private String precaution; //주의사항

    @Column(length = 1500)
    private String interaction; //상호작용

    @Column(length = 2500)
    private String sideEffect; //부작용

    private String storage; //보관법

    private String imageLink; //이미지

}
