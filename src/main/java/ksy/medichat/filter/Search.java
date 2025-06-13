package ksy.medichat.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Search {
    private String keyword;
    private String sortType;
    private String commonFilter;
    private Integer maxDistance;

    private Location location;
    private Date date;
}
