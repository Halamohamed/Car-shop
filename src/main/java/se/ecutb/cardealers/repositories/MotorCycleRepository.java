package se.ecutb.cardealers.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.cardealers.entities.MotorCycle;
@Repository
public interface MotorCycleRepository extends MongoRepository<MotorCycle,String> {
}
