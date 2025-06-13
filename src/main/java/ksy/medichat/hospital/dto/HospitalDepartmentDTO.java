package ksy.medichat.hospital.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import ksy.medichat.hospital.domain.HospitalDepartment;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalDepartmentDTO {
    private String name;
    private String description;
    private String enName;

    // Entity → DTO 변환
    public static HospitalDepartmentDTO toDTO(HospitalDepartment entity) {
        return HospitalDepartmentDTO.builder().name(entity.getName()).description(entity.getDescription()).enName(entity.getEnName()).build();
    }

    // DTO → Entity 변환
    public static HospitalDepartment toEntity(HospitalDepartmentDTO dto) {
        return HospitalDepartment.builder().name(dto.getName()).description(dto.getDescription()).enName(dto.getEnName()).build();
    }

}
