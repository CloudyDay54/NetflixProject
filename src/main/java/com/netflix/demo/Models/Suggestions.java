package com.netflix.demo.Models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="movie_suggestions")
public class Suggestions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "movie_title")
    @NotNull(groups = Create.class)
    private String movie_title;

    @Column(name = "movie_type")
    private boolean movie_type;

    public Suggestions(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public boolean isMovie_type() {
        return movie_type;
    }

    public void setMovie_type(boolean movie_type) {
        this.movie_type = movie_type;
    }
}
