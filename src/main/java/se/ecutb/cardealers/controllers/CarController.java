package se.ecutb.cardealers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.ecutb.cardealers.entities.Car;
import se.ecutb.cardealers.services.CarService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/shop/cars")
public class CarController {
    @Autowired
    private  CarService carService;


    @GetMapping
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String model,
                                                @RequestParam(required = false) String status,
                                                @RequestParam(required = false) Double price,
                                                @RequestParam(required = false) boolean sort){
        return ResponseEntity.ok(carService.getAllCars(brand,model,status,price,sort));
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id){
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @PostMapping
    public ResponseEntity<Car> saveCar(@Validated @RequestBody Car car){
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCar(@Validated @RequestBody Car car, @PathVariable String id){
        carService.updateCar(car,id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable String id){
        carService.deleteCar(id);
    }


    @GetMapping("/{brand}")
    public ResponseEntity<List<Car>> getCarByBrand(@PathVariable String brand){
        return ResponseEntity.ok(carService.getCarByBrand(brand));
    }

    @GetMapping("/{registerNo}")
    public ResponseEntity<Car> getCarByRegisterNo(@PathVariable String registerNo){
        return ResponseEntity.ok(carService.getCarByRegisterNo(registerNo));
    }

    @GetMapping("/{model}")
    public ResponseEntity<List<Car>> getCarByModel(@PathVariable String model){
        return ResponseEntity.ok(carService.getCarByModel(model));
    }

    @GetMapping("/{price}")
    public ResponseEntity<List<Car>> getCarByPrice(@PathVariable Double price){
        return ResponseEntity.ok(carService.getCarByPrice(price));
    }

    @GetMapping("/{carType}")
    public ResponseEntity<List<Car>> getCarByType(@PathVariable String carType){
        return ResponseEntity.ok(carService.getCarByType(carType));
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Car>> getCarByStatus(@PathVariable String status){
        return ResponseEntity.ok(carService.getCarByStatus(status));
    }
    @GetMapping("/{fuel}")
    public ResponseEntity<List<Car>> getCarByFuel(@PathVariable String fuel){
        return ResponseEntity.ok(carService.getCarByFuel(fuel));
    }

    @GetMapping("/{noOfSeat}")
    public ResponseEntity<List<Car>> getCarByNoOfSeat(@PathVariable int noOfSeat){
        return ResponseEntity.ok(carService.getCarByNoOfSeats(noOfSeat));
    }

    @GetMapping("/{gearbox}")
    public ResponseEntity<List<Car>> getCarByGearbox(@PathVariable String gearbox){
        return ResponseEntity.ok(carService.getCarByGearbox(gearbox));
    }

    @GetMapping("/{milNo}")
    public ResponseEntity<List<Car>> getCarByMilNo(@PathVariable int milNo){
        return ResponseEntity.ok(carService.getCarByMileNo(milNo));
    }

    @GetMapping("/{horsepower}")
    public ResponseEntity<List<Car>> getCarByHorsepower(@PathVariable int horsepower){
        return ResponseEntity.ok(carService.getCarByHorsepower(horsepower));
    }

}
