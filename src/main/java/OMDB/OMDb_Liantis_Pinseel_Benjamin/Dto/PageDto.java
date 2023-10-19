package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private int totalPages;
    private long totalElements;
    private List<T> responseDtos;

    public PageDto(int totalPages, long totalElements, List<T> responseDtos) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.responseDtos = responseDtos;
    }
}
