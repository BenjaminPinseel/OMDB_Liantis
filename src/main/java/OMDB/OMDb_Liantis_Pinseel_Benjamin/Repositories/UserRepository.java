package OMDB.OMDb_Liantis_Pinseel_Benjamin.Repositories;

import OMDB.OMDb_Liantis_Pinseel_Benjamin.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
