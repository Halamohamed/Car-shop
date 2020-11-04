package se.ecutb.cardealers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.ecutb.cardealers.entities.GearBox;
import se.ecutb.cardealers.services.GearBoxService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/shop/gearbox")
public class GearboxController {

    @Autowired
    private GearBoxService gearBoxService;

    @GetMapping
    public ResponseEntity<List<GearBox>> getAllGearbox(){
        return ResponseEntity.ok(gearBoxService.getAllGearbox());
    }
    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @GetMapping("/{id}")
    public ResponseEntity<GearBox> getGearboxById(@PathVariable String id){
        return ResponseEntity.ok(gearBoxService.getGearById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @PostMapping
    public ResponseEntity<GearBox> saveGearbox(@Validated @RequestBody GearBox gearBox){
        return ResponseEntity.ok(gearBoxService.saveGearbox(gearBox));
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGearbox(@Validated @RequestBody GearBox gearBox, @PathVariable String id){
        gearBoxService.updateGearbox(gearBox,id);
    }
    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGearbox(@PathVariable String id){
        gearBoxService.deleteGearbox(id);
    }
}
