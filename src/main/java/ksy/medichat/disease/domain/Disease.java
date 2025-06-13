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
    private String code;

    @Column(length = 500)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String department;

    private Long hit;
}
