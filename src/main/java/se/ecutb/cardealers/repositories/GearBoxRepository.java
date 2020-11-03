package se.ecutb.cardealers.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.cardealers.entities.GearBox;
@Repository
public interface GearBoxRepository extends MongoRepository<GearBox,String> {
}
