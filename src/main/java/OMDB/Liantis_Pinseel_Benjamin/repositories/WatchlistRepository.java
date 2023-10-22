package OMDB.Liantis_Pinseel_Benjamin.repositories;

import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, String> {
    List<Watchlist> findByUserId(String id);
}
