package se.ecutb.cardealers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.ecutb.cardealers.entities.AppUser;
import se.ecutb.cardealers.repositories.AppUserRepository;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService  {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "userCache")
     public List<AppUser>getAllUsers(){
         log.info("Request to find All Users");
        return userRepository.findAll();
     }
    @Cacheable(value = "userCache",key = "#id")
     public AppUser getUserById(String id){
         log.info("Request to find User by Id");
         return userRepository.findById(id).orElseThrow(()->new
                 ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Could not Find User by Id%",id)));
     }

     public AppUser findUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, // 404 -> Not found
                 String.format("Could not find the user by username %s.", username)));
     }
    @CachePut(value = "userCache",key = "#result.id")
     public AppUser saveUser(AppUser user){
         log.info("Request to save User");
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         return userRepository.save(user);
     }
    @CachePut(value = "userCache",key = "#id")
     public void updateUser(AppUser user,String id){
         log.info("Request to Update User ");
         if (!userRepository.existsById(id)){
             throw new
                     ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Could not Find User by Id%",id));
         }
         user.setId(id);
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
     }
     @CacheEvict(value = "userCache",key = "#id")
     public void deleteUser(String id){
         log.info("Request to Delete User");
         if (!userRepository.existsById(id)){
             throw new
                     ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Could not Find User by Id%",id));
         }
         userRepository.deleteById(id);
     }

}

