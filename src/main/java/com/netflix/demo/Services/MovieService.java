package com.netflix.demo.Services;

import com.netflix.demo.Models.Movie;
import com.netflix.demo.Models.User;

import java.util.List;

public interface MovieService {
    List<Movie> findAll();

    Movie findById(Long id);

    Movie create(User currentUser, Movie movie);

    Movie suggestMovie(User currentUser, Movie movie);

    Movie update(User currentUser, Movie movie);

    Movie update(User currentUser, Long id, Movie movie);

    void delete(User currentUser, Long id);
}
