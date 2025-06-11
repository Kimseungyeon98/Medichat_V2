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
    private String diseaseSymptoms;
    private String diseaseDepartment;
    private int diseaseHit;

    // Entity → DTO 변환
    public static DiseaseDTO toDTO(Disease entity) {
        return DiseaseDTO.builder().diseaseCode(entity.getDiseaseCode()).diseaseName(entity.getDiseaseName()).diseaseSymptoms(entity.getDiseaseSymptoms()).diseaseDepartment(entity.getDiseaseDepartment()).build();
    }

    // DTO → Entity 변환
    public static Disease toEntity(DiseaseDTO dto) {
        return Disease.builder().diseaseCode(dto.getDiseaseCode()).diseaseName(dto.getDiseaseName()).diseaseSymptoms(dto.getDiseaseSymptoms()).diseaseDepartment(dto.getDiseaseDepartment()).build();
    }
}
