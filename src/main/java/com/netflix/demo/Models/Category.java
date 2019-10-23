package com.netflix.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name =  "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "category")
    @NotNull(groups = Create.class)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "movie_categories",
        joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id")
    )
    private List<Movie> movies = new ArrayList<>();

    public Category(){}

    public interface Create{}

    public interface Update{}
}
