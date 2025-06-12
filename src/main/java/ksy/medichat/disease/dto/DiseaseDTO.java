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
    private String diseaseCode;
    private String diseaseName;
    private String diseaseDescription;
    private String diseaseDepartment;
    private Long diseaseHit;

    // Entity → DTO 변환
    public static DiseaseDTO toDTO(Disease entity) {
        return DiseaseDTO.builder().diseaseCode(entity.getDiseaseCode()).diseaseName(entity.getDiseaseName()).diseaseDescription(entity.getDiseaseDescription()).diseaseDepartment(entity.getDiseaseDepartment()).diseaseHit(entity.getDiseaseHit()).build();
    }

    // DTO → Entity 변환
    public static Disease toEntity(DiseaseDTO dto) {
        return Disease.builder().diseaseCode(dto.getDiseaseCode()).diseaseName(dto.getDiseaseName()).diseaseDescription(dto.getDiseaseDescription()).diseaseDepartment(dto.getDiseaseDepartment()).diseaseHit(dto.getDiseaseHit()).build();
    }
}
