package com.netflix.demo.Security;

import com.netflix.demo.Models.User;
import com.netflix.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.transaction.Transactional;

@Transactional
public class AuthProvider extends DaoAuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final User user = userRepository.findByUserID((String) auth.getPrincipal());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid userId or password");
        }

        boolean found = this.getPasswordEncoder().matches((String) auth.getCredentials(), user.getPassword());
        if(!found) {
            throw new BadCredentialsException("Invalid userId or password");
        }

        user.getRoles();
        user.getSuggestions();
        return new UsernamePasswordAuthenticationToken(user, auth.getCredentials(), auth.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
