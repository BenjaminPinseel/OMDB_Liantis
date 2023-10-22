package OMDB.Liantis_Pinseel_Benjamin.repositories;

import OMDB.Liantis_Pinseel_Benjamin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
