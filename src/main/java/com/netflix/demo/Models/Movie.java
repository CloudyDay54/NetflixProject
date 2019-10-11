package com.netflix.demo.Models;

//import org.hibernate.sql.Create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
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
    private String movie_title;

    @ManyToMany(mappedBy = "movies")
    private Set<Category> categories = new HashSet<>();

    @OneToOne(mappedBy = "movie")
    private Suggestion suggestion;

    @Column(name = "movie_type")
    private MovieType movie_type;

    private Movie(){}

    public interface Create{}

    public interface Update{}
}
