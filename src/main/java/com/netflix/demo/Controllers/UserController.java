package com.netflix.demo.Controllers;

import com.netflix.demo.Exceptions.UserExistsException;
import com.netflix.demo.Models.User;
import com.netflix.demo.Services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping(value = "{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping(value = "/register")
    public User register(@Validated(User.Create.class) @RequestBody User User){
        return userService.register(User);
    }
    
    @DeleteMapping(value = "{id}")
    public Boolean delete(
        @AuthenticationPrincipal User user,
        @PathVariable Long id
    ){
        userService.delete(user, id);
        return true;
    }

    @PatchMapping
    public User update(
        @AuthenticationPrincipal User currentUser,
        @Validated(User.Update.class) @RequestBody User user
    ){
        return userService.update(currentUser, user);
    }

    @PatchMapping(value = "{id}")
    public User update(
        @AuthenticationPrincipal User currentUser,
        @PathVariable Long id, @RequestBody User user
    ){
        return userService.update(currentUser, id, user);
    }

}
