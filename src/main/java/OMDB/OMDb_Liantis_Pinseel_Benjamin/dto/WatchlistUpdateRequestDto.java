package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import lombok.Data;

@Data
public class WatchlistUpdateRequestDto {
    private String id;
    private String userId;
    private String title;
    private String description;
}
