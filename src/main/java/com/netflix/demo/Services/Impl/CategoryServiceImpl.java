package com.netflix.demo.Services.Impl;

import com.netflix.demo.Exceptions.NotAllowedException;
import com.netflix.demo.Exceptions.NotFoundException;
import com.netflix.demo.Models.Category;
import com.netflix.demo.Models.Movie;
import com.netflix.demo.Models.User;
import com.netflix.demo.Repositories.CategoryRepository;
import com.netflix.demo.Services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("No category with id " + id + " found"));
    }

    @Override
    public Category create(User currentUser, Category category) {
        if(currentUser.hasRole("ADMIN")){

            return categoryRepository.save(category);

        } else {
            throw new NotAllowedException("User is not allowed to create an original movie");
        }
    }

    @Override
    public List<Movie> availableMovies(Long categoryId, String movieType) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("No category with id " + categoryId + " found"));

        return category.getMovies().stream()
                .filter(movie -> movie.getMovieType().label.equals(movieType))
                .collect(Collectors.toList());
    }
}
