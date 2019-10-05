package com.netflix.demo.Models;

//import org.hibernate.sql.Create;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "movie_title")
    @NotNull(groups = Create.class)
    private String movie_title;

    @Column(name = "category")
    private String category;

    @Column(name = "movie_type")
    private boolean movie_type;

    @OneToMany(mappedBy = "movies")
    private List<Categories> categories;

    public Movies(String movie_title, String category, boolean movie_type){
        this.movie_title = movie_title;
        this.category = category;
        this.movie_type = movie_type;
    }

    private Movies(){

    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isMovie_type() {
        return movie_type;
    }

    public void setMovie_type(boolean movie_type) {
        this.movie_type = movie_type;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}
