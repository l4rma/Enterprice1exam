package org.example.frontend.controller;


import org.example.TestFrontendApplicationRunner;
import org.example.backend.MovieWithAvgStars;
import org.example.backend.entity.Movie;
import org.example.backend.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestFrontendApplicationRunner.class)
public class MovieControllerTest {


//    @Autowired
//    private MovieService movieService;

    @Autowired
    private MovieController movieController;

    @Test
    public void shouldNotGetMoreThanMaxMovies() {
        List<Movie> list = movieController.getMovies(5);
        List<Movie> list2 = movieController.getMovies(7);

        assertEquals(5, list.size());
        assertEquals(7, list2.size());
    }

    @Test
    public void shouldGetMovieById() {
        // First movie (id=1) inserted by flyway is "The Shawshank Redemption"
        Movie movie = movieController.getMovieById(1L);

        assertEquals("The Shawshank Redemption", movie.getTitle());

        Long movieId = 123L;
        assertThrows(NoResultException.class, () -> movieController.getMovieById(movieId));
    }

    @Test
    public void shouldGetMoviesWithAvgStars() {
        List<MovieWithAvgStars> list = movieController.getMoviesWithAvgStars(1);
        Movie movie = list.get(0).getMovie();
        Double avgStars = list.get(0).getAvgStars();

        assertEquals("The Shawshank Redemption" ,movie.getTitle());
        assertEquals(3.142857142857143 ,avgStars);
    }
}
