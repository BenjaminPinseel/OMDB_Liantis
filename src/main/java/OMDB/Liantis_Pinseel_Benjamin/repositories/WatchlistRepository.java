package OMDB.Liantis_Pinseel_Benjamin.repositories;

import OMDB.Liantis_Pinseel_Benjamin.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, String> {
    List<Watchlist> findByUserId(String id);
}
