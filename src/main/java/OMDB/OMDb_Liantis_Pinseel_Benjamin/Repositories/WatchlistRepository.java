package OMDB.OMDb_Liantis_Pinseel_Benjamin.Repositories;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, String> {
}
