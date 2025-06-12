package ksy.medichat.hospital.dto;

import ksy.medichat.hospital.domain.HospitalDepartment;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalDepartmentDTO {
    private String departmentName;
    private String departmentDescription;
    private String departmentEnName;

    // Entity → DTO 변환
    public static HospitalDepartmentDTO toDTO(HospitalDepartment entity) {
        return HospitalDepartmentDTO.builder().departmentName(entity.getDepartmentName()).departmentDescription(entity.getDepartmentDescription()).departmentEnName(entity.getDepartmentEnName()).build();
    }

    // DTO → Entity 변환
    public static HospitalDepartment toEntity(HospitalDepartmentDTO dto) {
        return HospitalDepartment.builder().departmentName(dto.getDepartmentName()).departmentDescription(dto.getDepartmentDescription()).departmentEnName(dto.getDepartmentEnName()).build();
    }
}
