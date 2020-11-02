package se.ecutb.cardealers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Car>> getAllCars(){
        return ResponseEntity.ok(carService.getAllCars());
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
    public void updateCar(@Validated @RequestBody Car car, @PathVariable String id){
        carService.updateCar(car,id);
    }
    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable String id){
        carService.deleteCar(id);
    }

}
