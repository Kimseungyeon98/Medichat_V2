package ksy.medichat.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ksy.medichat.util.DurationFromNow;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDTO {

    private Long cboNum;
    private Long memNum;
    private Long cboType;

    @NotBlank
    private String cboTitle;

    @NotEmpty
    private String cboContent;

    private Integer cboHit;
    private String cboRdate;
    private String cboMdate;
    private Integer cboReport;

    // JOIN 결과
    private String memId;

    // 추가 정보
    private Integer reCnt;
    private Integer favCnt;

    public void setCboRdate(String cboRdate) {
        this.cboRdate = DurationFromNow.getTimeDiffLabel(cboRdate);
    }

    public void setCboMdate(String cboMdate) {
        this.cboMdate = DurationFromNow.getTimeDiffLabel(cboMdate);
    }
}