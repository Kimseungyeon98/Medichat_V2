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
    private String keyword;
    private String sortType;
    private String commonFilter;
    private int around;
    private double user_lat;
    private double user_lon;
}
