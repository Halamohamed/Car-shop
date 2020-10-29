package se.ecutb.cardealers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
     public List<AppUser>getAllUsers(){
         log.info("Request to find All Users");
        return userRepository.findAll();
     }
     public AppUser getUserbyId(String id){
         log.info("Request to find User by Id");
         return userRepository.findById(id).orElseThrow(()->new
                 ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Could not Find User by Id%",id)));
     }
     public AppUser saveUser(AppUser user){
         log.info("Request to save User");
         return userRepository.save(user);
     }
     public void uppdateUser(AppUser user,String id){
         log.info("Request to Uppdate User ");
         if (!userRepository.existsById(id)){
             throw new
                     ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Could not Find User by Id%",id));
         }
         user.setId(id);
         userRepository.save(user);
     }
     public void deleteUser(String id){
         log.info("Request to Delet User");
         if (!userRepository.existsById(id)){
             throw new
                     ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Could not Find User by Id%",id));
         }
         userRepository.deleteById(id);
     }
}

