package se.ecutb.cardealers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.ecutb.cardealers.entities.Car;
import se.ecutb.cardealers.repositories.CarRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    @Cacheable(value = "carCache")
    public List<Car> getAllCars(String brand, String model, String status, double price,boolean sort){
        log.info("Request to find all cars");
        log.warn("fresh data");
        var cars = carRepository.findAll();
        if(brand != null) cars = getCarByBrand(brand);

        if(model != null) cars = getCarByModel(model);

        if(status != null) cars = getCarByStatus(status);

        if(price != 0)   cars = getCarByPrice(price);

        if(sort)  cars.stream().sorted(Comparator.comparing(Car::getPrice));

        return cars;
    }

    @Cacheable(value = "carCache", key = "#id")
    public Car getCarById(String id){
        log.info("Request to find car by id");
        log.warn("fresh data");
        return carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Could not find car by id %s.", id)));
    }

    @CachePut(value = "carCache", key = "#result.id")
    public Car saveCar(Car car){
        log.info("Request to save car to database");
        log.warn("fresh data");
        return carRepository.save(car);
    }

    @CachePut(value = "carCache", key = "#id")
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

    @CacheEvict(value = "carCache", key = "#id")
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
        log.info("Request to get cars by brand");
        log.warn("fresh data");
        return carRepository.findAll().stream()
                .filter(car -> car.getBrand().contains(brand) ||
                        car.getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    public List<Car> getCarByModel(String model){
        log.info("Request to get cars by model");
        log.warn("fresh data");
        return carRepository.findAll().stream()
                .filter(car -> car.getModel().contains(model) ||
                        car.getModel().equals(model))
                .collect(Collectors.toList());
    }

    public List<Car> getCarByNoOfSeats(int numOfSeats){
        log.info("Request to get cars by numOfSeats");
        log.warn("fresh data");
        return carRepository.findAll().stream()
                .filter(car -> car.getNumOfSeats() == numOfSeats)
                .collect(Collectors.toList());
    }

    public Car getCarByRegisterNo(String registerNo){
        log.info("Request to get car by register number");
        log.warn("fresh data");
        var registerCar = carRepository.findAll();
        return registerCar.stream()
                .filter(car -> car.getRegisterNo().equals(registerNo))
                .findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Could not find car by register number %s.", registerNo)));
    }

    public List<Car> getCarByPrice(Double price){
        log.info("Request to get cars by price");
        log.warn("fresh data");
        var priceCar = carRepository.findAll();
        return priceCar.stream()
                .filter(car -> car.getPrice().equals(price))
                .collect(Collectors.toList());
    }
    public List<Car> getCarByType(String type){
        var typeCar = carRepository.findAll();
        return typeCar.stream()
                .filter(car -> car.getCarType().equals(type))
                .collect(Collectors.toList());
    }
    public List<Car> getCarByStatus(String status){
        var statusCar = carRepository.findAll();
        return statusCar.stream()
                .filter(car -> car.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    public List<Car> getCarByFuel(String fuel){
        var fuelCar = carRepository.findAll();
        return fuelCar.stream()
                .filter(car -> car.getFuel().equals(fuel))
                .collect(Collectors.toList());
    }
    public List<Car> getCarByGearbox(String gearbox){
        var gearboxCar = carRepository.findAll();
        return gearboxCar.stream()
                .filter(car -> car.getGearbox().equals(gearbox))
                .collect(Collectors.toList());
    }
    public List<Car> getCarByMileNo(int milNo){
        var milNoCar = carRepository.findAll();
        return milNoCar.stream()
                .filter(car -> car.getMileNo() == milNo)
                .collect(Collectors.toList());
    }
    public List<Car> getCarByHorsepower(int horsepower){
        var horsepowerCar = carRepository.findAll();
        return horsepowerCar.stream()
                .filter(car -> car.getHorsepower()==horsepower)
                .collect(Collectors.toList());
    }
}
