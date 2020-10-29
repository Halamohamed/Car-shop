package se.ecutb.cardealers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.ecutb.cardealers.entities.Car;
import se.ecutb.cardealers.repositories.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public List<Car> getAllCars(){
        log.info("Request to find all cars");
        log.warn("fresh data");
        return carRepository.findAll();

    }

    public Car getCarById(String id){
        log.info("Request to find car by id");
        log.warn("fresh data");
        return carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Could not find car by id %s.", id)));
    }

    public Car saveCar(Car car){
        log.info("Request to save car to database");
        log.warn("fresh data");
       return carRepository.save(car);
    }

    public void updateCar(Car car, String id){
        log.info("Request to update car");
        log.warn("fresh data");
        if(!carRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find car by id %s.", id));
        }
        car.setId(id);
        carRepository.save(car);
    }

    public void deleteCar(String id){
        log.info("Request to delete car by id");
        log.warn("fresh data");
        if(!carRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find car by id %s.", id));
        }
        carRepository.deleteById(id);
    }

    public List<Car> getCarByBrand(String brand){
        return carRepository.findAll().stream()
                .filter(car -> car.getBrand().contains(brand) ||
                        car.getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    public List<Car> getCarByModel(String model){
        return carRepository.findAll().stream()
                .filter(car -> car.getModel().contains(model) ||
                        car.getModel().equals(model))
                .collect(Collectors.toList());
    }

    public List<Car> getCarByNoOfSeats(int numOfSeats){
        return carRepository.findAll().stream()
                .filter(car -> car.getNumOfSeats() == numOfSeats)
                .collect(Collectors.toList());
    }

    public Car getCarByRegisterNo(String registerNo){
        var registerCar = carRepository.findAll();
        return registerCar.stream()
                .filter(car -> car.getRegisterNo().equals(registerNo))
                .findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Could not find car by register number %s.", registerNo)));
    }

    public List<Car> getCarByPrice(Double price){
        var priceCar = carRepository.findAll();
        return priceCar.stream()
                .filter(car -> car.getPrice().equals(price))
                .collect(Collectors.toList());
    }
}
