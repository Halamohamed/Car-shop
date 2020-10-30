package se.ecutb.cardealers.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.cardealers.entities.AppUser;
@Repository
public interface AppUserRepository extends
        MongoRepository <AppUser,String> {

}
