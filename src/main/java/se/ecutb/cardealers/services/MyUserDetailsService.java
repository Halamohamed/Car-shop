package se.ecutb.cardealers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.ecutb.cardealers.entities.AppUser;

import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userService.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));

    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(AppUser user) {
        return user.getAcl().stream()
                .map(authority -> new SimpleGrantedAuthority("ROLE_" +authority.toString()))
                .collect(Collectors.toList());
    }
}
