package se.ecutb.cardealers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.ecutb.cardealers.entities.MotorCycle;
import se.ecutb.cardealers.repositories.MotorCycleRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MotoCycleService {
    private final MotorCycleRepository repository;

    public List<MotorCycle> getAllMotorCycle(){
        return repository.findAll();
    }

    public MotorCycle getById(String id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Could not found MotorCycle by id s%.", id)));
    }

    public MotorCycle save(MotorCycle motorCycle){
        return repository.save(motorCycle);
    }
    public void update(MotorCycle motorCycle, String id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not found MotorCycle by id s%.", id));
        }
        motorCycle.setId(id);
        repository.save(motorCycle);
    }

    public void delete(String id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not found MotorCycle by id s%.", id));
        }
        repository.deleteById(id);
    }
}
