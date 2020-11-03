package se.ecutb.cardealers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.ecutb.cardealers.entities.MotorCycle;
import se.ecutb.cardealers.services.MotoCycleService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/shop/motorcycle")
public class MotorCycleController {
    @Autowired
    private MotoCycleService service;

    @GetMapping
    public ResponseEntity<List<MotorCycle>> findAllMotorCycle(){
        return ResponseEntity.ok(service.getAllMotorCycle());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MotorCycle> findById(@PathVariable String id){
        return ResponseEntity.ok(service.getById(id));
    }
    @PostMapping
    public ResponseEntity<MotorCycle> saveMotorCycle(@Validated @RequestBody MotorCycle motorCycle){
        return ResponseEntity.ok(service.save(motorCycle));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMotorCycle(@Validated @RequestBody MotorCycle motorCycle,@PathVariable String id){
        service.update(motorCycle,id);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMotorCycle(@PathVariable String id){
        service.delete(id);
    }
}
