package ksy.medichat.community.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityReplyDTO {

    private Long creNum;
    private Long cboNum;
    private Long memNum;
    private String creContent;
    private String creRdate;
    private String creMdate;
    private Long creLevel;
    private Long creRef;
    private Long creReport;

    // JOIN으로 가져온 값들
    private String memId;
    private int clickNum;
    private int refavCnt;
    private String parentId;
    private int replyCnt;
    private String postTitle;

    public void setCreRdate(String creRdate) {
        this.creRdate = ksy.medichat.util.DurationFromNow.getTimeDiffLabel(creRdate);
    }

    public void setCreMdate(String creMdate) {
        this.creMdate = ksy.medichat.util.DurationFromNow.getTimeDiffLabel(creMdate);
    }
}