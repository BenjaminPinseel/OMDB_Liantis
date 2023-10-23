package OMDB.Liantis_Pinseel_Benjamin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WatchlistUpdateRequestDto {
    private String id;
    private String userId;
    private String title;
    private String description;
}
