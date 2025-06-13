package ksy.medichat.disease.dto;

import ksy.medichat.disease.domain.Disease;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiseaseDTO {
    private String code;
    private String name;
    private String description;
    private String department;
    private Long hit;

    // Entity → DTO 변환
    public static DiseaseDTO toDTO(Disease entity) {
        return DiseaseDTO.builder().code(entity.getCode()).name(entity.getName()).description(entity.getDescription()).department(entity.getDepartment()).hit(entity.getHit()).build();
    }

    // DTO → Entity 변환
    public static Disease toEntity(DiseaseDTO dto) {
        return Disease.builder().code(dto.getCode()).name(dto.getName()).description(dto.getDescription()).department(dto.getDepartment()).hit(dto.getHit()).build();
    }
}
