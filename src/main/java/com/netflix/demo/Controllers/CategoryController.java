package com.netflix.demo.Controllers;

import com.netflix.demo.Models.Category;
import com.netflix.demo.Models.Movie;
import com.netflix.demo.Models.User;
import com.netflix.demo.Services.CategoryService;
import com.netflix.demo.Services.MovieService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "categories", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
})
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "{id}/movies")
    public List<Movie> availableMovies(
            @PathVariable(name = "id") Long categoryId,
            @RequestParam(name = "type") String movieType
    ){
        return categoryService.availableMovies(categoryId, movieType);
    }

    @PostMapping
    public Category create(
            @AuthenticationPrincipal User user,
            @Validated(Movie.Create.class) @RequestBody Category category
    ) {
        return categoryService.create(user, category);
    }
}
