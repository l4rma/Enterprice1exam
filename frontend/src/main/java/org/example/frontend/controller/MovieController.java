package org.example.frontend.controller;

import org.example.backend.MovieWithAvgStars;
import org.example.backend.entity.Movie;
import org.example.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class MovieController {

    @Autowired
    private MovieService movieService;

    public List<Movie> getMovies(int max) { return movieService.getMovies(max); }

    public Movie getMovieById(Long movieId) { return movieService.getMovieById(movieId); }

    public List<MovieWithAvgStars> getMoviesWithAvgStars(int max) { return movieService.getMoviesWithAvgStars(max); }
}
