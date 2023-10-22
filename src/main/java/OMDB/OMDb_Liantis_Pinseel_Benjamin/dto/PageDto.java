package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class PageDto<T> {
    private int totalPages;
    private long totalElements;
    private Set<T> responseDtos;

    public PageDto(int totalPages, long totalElements, Set<T> responseDtos) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.responseDtos = responseDtos;
    }
}
