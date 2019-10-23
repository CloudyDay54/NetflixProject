package com.netflix.demo.Services;

import com.netflix.demo.Models.Category;
import com.netflix.demo.Models.Movie;
import com.netflix.demo.Models.User;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category create(User currentUser, Category category);

    List<Movie> availableMovies(Long categoryId, String movieType);
}