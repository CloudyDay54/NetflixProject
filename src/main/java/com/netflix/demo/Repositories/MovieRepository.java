package com.netflix.demo.Repositories;

import com.netflix.demo.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
