package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
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
