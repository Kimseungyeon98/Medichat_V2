package ksy.medichat.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pageable {

    private int page=0;
    private int size=10;

    public org.springframework.data.domain.Pageable getPageable() {
        return PageRequest.of(page, size);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
