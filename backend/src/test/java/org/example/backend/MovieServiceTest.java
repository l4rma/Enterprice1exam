package org.example.backend;

import org.example.backend.entity.Movie;
import org.example.backend.entity.Review;
import org.example.backend.service.MovieService;
import org.example.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private UserService userService = new UserService();

    @Autowired
    private MovieService movieService = new MovieService();

    @Autowired
    private DeleterService deleterService = new DeleterService();
/**
 * Created by arcuri82
 * https://github.com/arcuri82/testing_security_development_enterprise_systems
 */
    @BeforeEach
    @AfterEach
    public void clearDatabase(){
        deleterService.deleteEntities(Review.class);
        deleterService.deleteEntities(Movie.class);
    }



    @Test
    public void shouldCreateMovieWithService() {
        boolean created = movieService.createMovie("title", 2000L, "director", "genre");
        assertEquals(created, true);
    }

    @Test
    public void shouldListMoviesWithService() {
        movieService.createMovie("title", 2000L, "director", "genre");
        movieService.createMovie("title2", 2000L, "director", "genre");
        movieService.createMovie("title3", 2000L, "director", "genre");
        List<Movie> list = movieService.getMoviesByYearOfRelease(10);
        assertEquals(list.size(), 3);
    }

    @Test
    public void shouldDeleteMovieWithService() {
        movieService.createMovie("title", 2000L, "director", "genre");
        List<Movie> list = movieService.getMoviesByYearOfRelease(10);

        assertEquals(list.size(), 1);

        long id = list.get(0).getId();
        movieService.deleteMovie(id);
        List<Movie> listAfterDelete = movieService.getMoviesByYearOfRelease(10);

        assertEquals(listAfterDelete.size(), 0);
    }

    @Test
    public void shouldNotDeleteNonExistingMovie() {
        movieService.createMovie("title", 2000L, "director", "genre");
        List<Movie> list = movieService.getMoviesByYearOfRelease(10);

        assertEquals(list.size(), 1);

        long id = list.get(0).getId();
        movieService.deleteMovie(1234);
        List<Movie> listAfterDelete = movieService.getMoviesByYearOfRelease(10);

        assertEquals(listAfterDelete.size(), 1);
    }
}
