package com.netflix.demo.Services.Impl;

import com.netflix.demo.Exceptions.NotAllowedException;
import com.netflix.demo.Exceptions.NotFoundException;
import com.netflix.demo.Models.*;
import com.netflix.demo.Repositories.CategoryRepository;
import com.netflix.demo.Repositories.MovieRepository;
import com.netflix.demo.Repositories.SuggestionRepository;
import com.netflix.demo.Services.MovieService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final SuggestionRepository suggestionRepository;
    private final CategoryRepository categoryRepository;

    public MovieServiceImpl(MovieRepository movieRepository,
                            SuggestionRepository suggestionRepository,
                            CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.suggestionRepository = suggestionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(Long id){
        return movieRepository.findById(id).orElseThrow(() ->
                new NotFoundException("No movie with id " + id + " found"));
    }

    @Transactional
    @Override
    public Movie create(User currentUser, Movie movie){
        if(currentUser.hasRole("ADMIN")){

            Movie m = collectMovie(movie);
            m.setMovieType(MovieType.ORIGINAL);
            m = movieRepository.save(m);

            Movie finalM = m;
            m.getCategories().forEach(category -> {
                category.getMovies().add(finalM);
                categoryRepository.save(category);
            });

            return m;

        } else {
            throw new NotAllowedException("User is not allowed to create an original movie");
        }
    }

    @Transactional
    public Movie suggestMovie(User currentUser, Movie movie){
        if(currentUser.hasRole("NORMAL_USER")){
            Movie m = collectMovie(movie);
            m.setMovieType(MovieType.SUGGESTED);
            m = movieRepository.save(m);

            Movie finalM = m;
            m.getCategories().forEach(category -> {
                category.getMovies().add(finalM);
                categoryRepository.save(category);
            });

            Suggestion suggestion = new Suggestion();
            suggestion.setOwner(currentUser);
            suggestion.setMovie(m);
            suggestionRepository.save(suggestion);
            return m;
        } else {
            throw new NotAllowedException("User is not allowed to suggest a movie");
        }
    }

    @Override
    public void delete(User currentUser, Long id) {
        if(currentUser.hasRole("ADMIN")) {
            movieRepository.deleteById(id);
        } else if(currentUser.hasRole("NORMAL_USER")) {
            Movie movie = findById(id);

            if (movie.getMovieType() == MovieType.SUGGESTED) {

                String ownerId = movie.getSuggestion().getOwner().getUserID();
                if(ownerId.equals(currentUser.getUserID())){
                    movieRepository.deleteById(id);
                } else {
                    throw new NotAllowedException("User is not allowed to delete a movie he/she did not suggest.");
                }

            } else {
                throw new NotAllowedException("User is not allowed to delete an original movie.");
            }

        } else {
            throw new NotAllowedException("User is not allowed to delete a movie.");
        }
    }

    @Override
    public Movie update(User currentUser, Movie movie) {
        return this.update(currentUser, movie.getId(), movie);
    }

    @Override
    public Movie update(User currentUser, Long id, Movie update) {
        if(currentUser.hasRole("ADMIN")) {
            Movie m = updateMovie(findById(id), update);
            return movieRepository.save(m);
        } else if(currentUser.hasRole("NORMAL_USER")) {
            Movie toUpdate = findById(id);

            if (toUpdate.getMovieType() == MovieType.SUGGESTED) {

                String ownerId = toUpdate.getSuggestion().getOwner().getUserID();
                if(ownerId.equals(currentUser.getUserID())){
                    Movie m = updateMovie(toUpdate, update);
                    return movieRepository.save(m);
                } else {
                    throw new NotAllowedException("User is not allowed to update a movie he/she did not suggest.");
                }

            } else {
                throw new NotAllowedException("User is not allowed to update an original movie.");
            }
        } else {
            throw new NotAllowedException("User is not allowed to update a movie.");
        }
    }

    private Movie collectMovie(Movie newMovie){
        Movie m = new Movie();
        m.setMovieTitle(newMovie.getMovieTitle());
        m.setYearReleased(newMovie.getYearReleased());
        m.setCategories(newMovie.getCategories().stream().map(category -> categoryRepository.findById(category.getId()).orElseThrow(() ->
                new NotFoundException("No category with id " + category.getId() + " found"))).collect(Collectors.toSet()));
        return m;
    }

    private Movie updateMovie(Movie toUpdate, Movie update){
        if (update.getMovieTitle() != null) toUpdate.setMovieTitle(update.getMovieTitle());
        if (update.getYearReleased() != null) toUpdate.setYearReleased(update.getYearReleased());
        if (update.getCategories() != null) toUpdate.setCategories(update.getCategories().stream().map(category -> categoryRepository.findById(category.getId()).orElseThrow(() ->
                new NotFoundException("No category with id " + category.getId() + " found"))).collect(Collectors.toSet()));
        return toUpdate;
    }
}
