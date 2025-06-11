package ksy.medichat.drug.dto;

import ksy.medichat.drug.domain.Drug;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DrugDTO {
    private Long drugNum; // 일련번호
    private String drugName; //의약품명
    private String drugCompany; //업체명
    private String drugEffect; //효능
    private String drugDosage; //복용량
    private String drugWarning; //경고
    private String drugPrecaution; //주의사항
    private String drugInteraction; //상호작용
    private String drugSideEffect; //부작용
    private String drugStorage; //보관법
    private String drugImageLink; //이미지

    // Entity → DTO 변환
    public static DrugDTO toDTO(Drug entity) {
        return DrugDTO.builder().drugNum(entity.getDrugNum()).drugName(entity.getDrugName()).drugCompany(entity.getDrugCompany()).drugEffect(entity.getDrugEffect()).drugDosage(entity.getDrugDosage()).drugWarning(entity.getDrugWarning()).drugPrecaution(entity.getDrugPrecaution()).drugInteraction(entity.getDrugInteraction()).drugSideEffect(entity.getDrugSideEffect()).drugStorage(entity.getDrugStorage()).drugImageLink(entity.getDrugImageLink()).build();
    }

    // DTO → Entity 변환
    public static Drug toEntity(DrugDTO dto) {
        return Drug.builder().drugNum(dto.getDrugNum()).drugName(dto.getDrugName()).drugCompany(dto.getDrugCompany()).drugEffect(dto.getDrugEffect()).drugDosage(dto.getDrugDosage()).drugWarning(dto.getDrugWarning()).drugPrecaution(dto.getDrugPrecaution()).drugInteraction(dto.getDrugInteraction()).drugSideEffect(dto.getDrugSideEffect()).drugStorage(dto.getDrugStorage()).drugImageLink(dto.getDrugImageLink()).build();
    }
}
