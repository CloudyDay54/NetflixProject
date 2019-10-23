package com.netflix.demo.Services.Impl;

import com.netflix.demo.Exceptions.NotAllowedException;
import com.netflix.demo.Exceptions.NotFoundException;
import com.netflix.demo.Exceptions.UserExistsException;
import com.netflix.demo.Models.User;
import com.netflix.demo.Repositories.RoleRepository;
import com.netflix.demo.Repositories.UserRepository;
import com.netflix.demo.Services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("No user with id " + id + " found"));
    }

    @Transactional
    @Override
    public User register(User user){
        if (userExists(user.getUserID())) {
            throw new UserExistsException(
                    "There already is an account with this user\'s id: "
                            +  user.getUserID());
        } else {
            User u = collectUser(user);
            u.setRoles(Collections.singletonList(roleRepository.findByName("NORMAL_USER")));
            return userRepository.save(u);
        }
    }

    private boolean userExists(String userId) {
        User user = userRepository.findByUserID(userId);
        return user != null;
    }

    @Override
    public void delete(User currentUser, Long id) {
        if(currentUser.hasRole("ADMIN")) {
            userRepository.deleteById(id);
        } else if(currentUser.hasRole("NORMAL_USER")) {
            User user = findById(id);

            if(user.getUserID().equals(currentUser.getUserID())){
                userRepository.deleteById(id);
                // TODO: logout
            } else {
                throw new NotAllowedException("User is not allowed to delete another user.");
            }

        } else {
            throw new NotAllowedException("User is not allowed to delete a user.");
        }
    }

    @Override
    public User update(User currentUser, User user) {
        return this.update(currentUser, user.getId(), user);
    }

    @Override
    public User update(User currentUser, Long id, User update) {
        if (update.getUserID() != null && userExists(update.getUserID())) {
            throw new UserExistsException(
                    "There already is an account with this user\'s id: "
                            +  update.getUserID());
        } else {
            if(currentUser.hasRole("ADMIN")) {
                User u = updateUser(findById(id), update);
                return userRepository.save(u);
            } else if(currentUser.hasRole("NORMAL_USER")) {
                User toUpdate = findById(id);


                if(toUpdate.getUserID().equals(currentUser.getUserID())){
                    User u = updateUser(toUpdate, update);
                    return userRepository.save(u);
                } else {
                    throw new NotAllowedException("User is not allowed to update another user.");
                }

            } else {
                throw new NotAllowedException("User is not allowed to update a user.");
            }
        }
    }

    private User collectUser(User newUser){
        User m = new User();
        m.setUserID(newUser.getUserID());
        m.setName(newUser.getName());
        m.setPassword(new BCryptPasswordEncoder(11).encode(newUser.getPassword()));
        return m;
    }

    private User updateUser(User toUpdate, User update){
        if (update.getUserID() != null) toUpdate.setUserID(update.getUserID());
        if (update.getName() != null) toUpdate.setName(update.getName());
        if (update.getPassword() != null) toUpdate.setPassword(new BCryptPasswordEncoder(11).encode(update.getPassword()));
        return toUpdate;
    }
}
