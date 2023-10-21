package OMDB.OMDb_Liantis_Pinseel_Benjamin.entities;

import lombok.Data;

@Data
public class OmdbFilters {
    private String imdbId;
    private String title;
    private String type;
    private String year;
    private String plot;
    private String returnType;
    private String searchTerm;
    private Integer page;
    private String callback;
    private String version;
}
