package se.ecutb.cardealers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.ecutb.cardealers.entities.GearBox;
import se.ecutb.cardealers.repositories.GearBoxRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GearBoxService {

    private final GearBoxRepository repository;

    public List<GearBox> getAllGearbox(){
        return repository.findAll();
    }

    public GearBox getGearById(String id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Could not find Gearbox by id %s.", id)));
    }

    public GearBox saveGearbox(GearBox gearBox){
        return repository.save(gearBox);
    }

    public void updateGearbox(GearBox gearBox, String id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find Gearbox by id %s.", id));
        }
        gearBox.setId(id);
        repository.save(gearBox);

    }
    public void deleteGearbox(String id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Could not find Gearbox by id %s.", id));
        }
        repository.deleteById(id);
    }

}
