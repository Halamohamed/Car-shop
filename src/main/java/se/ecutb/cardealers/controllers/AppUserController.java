package se.ecutb.cardealers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.ecutb.cardealers.entities.AppUser;
import se.ecutb.cardealers.services.AppUserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/shop/users")
public class AppUserController {
    @Autowired
    private AppUserService userService;

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER", "ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<AppUser>>getAllUsers(){
       return ResponseEntity.ok(userService.getAllUsers());
    }

    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @PostMapping
    public ResponseEntity<AppUser> saveUser(@Validated @RequestBody AppUser user){
        return ResponseEntity.ok(userService.saveUser(user));
    }
    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody AppUser user,@PathVariable String id){
        userService.updateUser(user,id);
    }
    @Secured({"ROLE_ADMIN", "ROLE_VD", "ROLE_MANAGER"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        userService.deleteUser(id);
    }
}
