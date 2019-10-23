package com.netflix.demo.Services;

import com.netflix.demo.Models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User register(User user);

    User update(User currentUser, User user);

    User update(User currentUser, Long id, User user);

    void delete(User currentUser, Long id);
}
