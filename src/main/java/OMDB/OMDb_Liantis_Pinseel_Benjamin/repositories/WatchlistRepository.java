package OMDB.OMDb_Liantis_Pinseel_Benjamin.repositories;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, String> {
}
