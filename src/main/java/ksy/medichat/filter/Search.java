package ksy.medichat.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchFilter {
    private String keyword="";
    private String sortType="NEAR";
    private String commonFilter;
    private Integer maxDistance;
}
