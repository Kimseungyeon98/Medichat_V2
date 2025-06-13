package ksy.medichat.drug.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import ksy.medichat.drug.domain.Drug;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DrugDTO {
    private Long code; // 일련번호
    private String name; //의약품명
    private String company; //업체명
    private String effect; //효능
    private String dosage; //복용량
    private String warning; //경고
    private String precaution; //주의사항
    private String interaction; //상호작용
    private String sideEffect; //부작용
    private String storage; //보관법
    private String imageLink; //이미지

    // Entity → DTO 변환
    public static DrugDTO toDTO(Drug entity) {
        return DrugDTO.builder().code(entity.getCode()).name(entity.getName()).company(entity.getCompany()).effect(entity.getEffect()).dosage(entity.getDosage()).warning(entity.getWarning()).precaution(entity.getPrecaution()).interaction(entity.getInteraction()).sideEffect(entity.getSideEffect()).storage(entity.getStorage()).imageLink(entity.getImageLink()).build();
    }

    // DTO → Entity 변환
    public static Drug toEntity(DrugDTO dto) {
        return Drug.builder().code(dto.getCode()).name(dto.getName()).company(dto.getCompany()).effect(dto.getEffect()).dosage(dto.getDosage()).warning(dto.getWarning()).precaution(dto.getPrecaution()).interaction(dto.getInteraction()).sideEffect(dto.getSideEffect()).storage(dto.getStorage()).imageLink(dto.getImageLink()).build();
    }
}
