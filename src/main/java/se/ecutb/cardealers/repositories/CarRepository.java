package se.ecutb.cardealers.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.cardealers.entities.Car;
@Repository
public interface CarRepository extends MongoRepository<Car,String> {
}
