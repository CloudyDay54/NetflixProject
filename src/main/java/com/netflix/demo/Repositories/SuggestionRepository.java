package com.netflix.demo.Repositories;

import com.netflix.demo.Models.Movie;
import com.netflix.demo.Models.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
