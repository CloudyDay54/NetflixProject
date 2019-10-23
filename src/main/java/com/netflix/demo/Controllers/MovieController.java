package com.netflix.demo.Controllers;

import com.netflix.demo.Models.Movie;
import com.netflix.demo.Models.User;
import com.netflix.demo.Services.MovieService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "movies", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
})
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll(){
        return movieService.findAll();
    }

    @GetMapping(value = "{id}")
    public Movie findById(@PathVariable Long id){
        return movieService.findById(id);
    }

    @PostMapping
    public Movie create(
        @AuthenticationPrincipal User user,
        @Validated(Movie.Create.class) @RequestBody Movie movie
    ) {
        return movieService.create(user, movie);
    }

    @PostMapping(value = "/suggest-movie")
    public Movie suggestMovie(
        @AuthenticationPrincipal User user,
        @Validated(Movie.Create.class) @RequestBody Movie movie
    ) {
        return movieService.suggestMovie(user, movie);
    }

    @DeleteMapping(value = "{id}")
    public Boolean delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ){
        movieService.delete(user, id);
        return true;
    }

    @PatchMapping
    public Movie update(
            @AuthenticationPrincipal User user,
            @Validated(Movie.Update.class) @RequestBody Movie movie
    ){
        return movieService.update(user, movie);
    }

    @PatchMapping(value = "{id}")
    public Movie update(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Validated(Movie.Update.class) @RequestBody Movie movie
    ){
        return movieService.update(user, id, movie);
    }
}
