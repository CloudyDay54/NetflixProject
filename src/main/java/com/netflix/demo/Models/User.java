package com.netflix.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "name")
    @NotNull(groups = Create.class)
    @NotEmpty(groups = Create.class)
    private String name;

    @Column(
        name = "user_id",
        unique = true
    )
    @NotNull(groups = Create.class)
    @NotEmpty(groups = Create.class)
    private String userID;

    @Column(name = "password")
    @NotNull(groups = Create.class)
    @NotEmpty(groups = Create.class)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Suggestion> suggestions;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;


    public User() {}

    public User(String userId) {
        this.setUserID(userId);
    }

    public interface Create{}

    public interface Update{}

    public boolean hasRole(final String role) {
        return this.getRoles().stream().anyMatch(r -> r.getName().equals(role));
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
