package se.ecutb.cardealers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.ecutb.cardealers.entities.AppUser;
import se.ecutb.cardealers.services.AppUserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class AppUserController {
    @Autowired
    private AppUserService userService;
    @GetMapping
    public ResponseEntity<List<AppUser>>getAllUsers(){
       return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserbyId(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserbyId(id));
    }
    @PostMapping
    public ResponseEntity<AppUser> saveUser(@Validated @RequestBody AppUser user){
        return ResponseEntity.ok(userService.saveUser(user));
    }
    @PutMapping("/{id}")
    public void uppdate(@Validated @RequestBody AppUser user,@PathVariable String id){
        userService.uppdateUser(user,id);
    }
    @DeleteMapping("/{id}")
    public void delet(@PathVariable String id){
        userService.deleteUser(id);
    }
}
