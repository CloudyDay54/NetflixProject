package com.netflix.demo.Config;

import com.netflix.demo.Models.Role;
import com.netflix.demo.Models.User;
import com.netflix.demo.Repositories.RoleRepository;
import com.netflix.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
 
    private boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;

        Role adminRole = createRoleIfNotFound("ADMIN");
        Role normalUserRole = createRoleIfNotFound("NORMAL_USER");

        createUserIfNotFound(adminRole);
 
        alreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(String name) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    public void createUserIfNotFound(Role adminRole) {

        User user = userRepository.findByUserID("super_admin");
        if (user == null) {
            user = new User();
            user.setName("Admin");
            user.setUserID("super_admin");
            user.setPassword(passwordEncoder.encode("12345678"));
            user.setRoles(Collections.singletonList(adminRole));
            userRepository.save(user);
        }
    }
}