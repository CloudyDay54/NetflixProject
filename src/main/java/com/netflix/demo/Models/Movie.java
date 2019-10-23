package com.netflix.demo.Models;

//import org.hibernate.sql.Create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "movie_title")
    @NotNull(groups = Create.class)
    @NotEmpty(groups = Create.class)
    private String movieTitle;

    @Column(name = "year_released")
    @NotNull(groups = Create.class)
    @NotEmpty(groups = Create.class)
    private String yearReleased;

    @Column(name = "movie_type")
    private MovieType movieType;

    @ManyToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<>();

    @OneToOne(mappedBy = "movie", optional = true, cascade = CascadeType.ALL)
    private Suggestion suggestion;

    public Movie(){}

    public interface Create{}

    public interface Update{}
}
