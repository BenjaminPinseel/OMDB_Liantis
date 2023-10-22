package OMDB.OMDb_Liantis_Pinseel_Benjamin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistCreateDto {
    @NotNull
    @Size(min = 1)
    private String title;

    @NotNull
    @Size(min = 1)
    private String description;
}
