package OMDB.OMDb_Liantis_Pinseel_Benjamin.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RatingDto {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Value")
    private String value;
}
