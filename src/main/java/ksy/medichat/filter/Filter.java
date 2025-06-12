package ksy.medichat.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Filter {
    private String keyword="";
    private String sortType;
    private String commonFilter;

    private String time;
    private int day;

    private Integer around;
    private Double user_lat;
    private Double user_lon;
}
