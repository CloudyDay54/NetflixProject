package com.netflix.demo.Security;

import com.netflix.demo.Models.Role;
import com.netflix.demo.Models.User;
import com.netflix.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
  
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String userId)
      throws UsernameNotFoundException {
  
        User user = userRepository.findByUserID(userId);
        if (user == null) {
            throw new UsernameNotFoundException(
              "No user found with userId: "+ userId);
        }

        user.getRoles();
        user.getSuggestions();
        return  new org.springframework.security.core.userdetails.User(
            user.getUserID(),
            user.getPassword().toLowerCase(), true, true,
                true, true,
            getAuthorities(user.getRoles())
        );
    }
     
    private static List<GrantedAuthority> getAuthorities (List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}