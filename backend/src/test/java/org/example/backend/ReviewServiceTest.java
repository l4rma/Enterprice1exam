package org.example.backend;

import org.example.backend.entity.Movie;
import org.example.backend.entity.Review;
import org.example.backend.entity.ReviewId;
import org.example.backend.entity.User;
import org.example.backend.service.MovieService;
import org.example.backend.service.ReviewService;
import org.example.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpellCheckingInspection")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private final UserService userService = new UserService();

    @Autowired
    private final MovieService movieService = new MovieService();

    @Autowired
    private final ReviewService reviewService = new ReviewService();

    @Autowired
    private final DeleterService deleterService = new DeleterService();

    @BeforeEach
    @AfterEach
    public void clearDatabase(){

        deleterService.deleteEntities(Review.class);
        deleterService.deleteEntities(Movie.class);
    }

    public void createAMovieAndSomeReviews() {
        for (int i = 0; i < 5; i++) {
            String username = "Test_User_"+i;
            String password = "im_a_robot";
            userService.createUser(username, password);
        }

        movieService.createMovie("title", 2000L, "director", "genre");
        movieService.createMovie("title2", 2002L, "director2", "genre2");

        List<Movie> movieList = movieService.getMoviesByYearOfRelease(10);

        reviewService.createReview(movieList.get(0).getId(), userService.getUserByUsername("Test_User_0").getUsername(), "Denne filmen var bare drit!", 1L);
        reviewService.createReview(movieList.get(0).getId(), userService.getUserByUsername("Test_User_1").getUsername(), "Denne filmen var knallgod!", 5L);
        reviewService.createReview(movieList.get(0).getId(), userService.getUserByUsername("Test_User_2").getUsername(), "Denne filmen var dårlig!", 2L);
        reviewService.createReview(movieList.get(0).getId(), userService.getUserByUsername("Test_User_3").getUsername(), "Denne filmen var god!", 4L);
        reviewService.createReview(movieList.get(0).getId(), userService.getUserByUsername("Test_User_4").getUsername(), "Denne filmen var grei!", 3L);
    }

    @Test
    public void shouldCreateReviewWithService() {
        String username = "Person";
        String password = "password";
        userService.createUser(username, password);
        movieService.createMovie("title", 2000L, "director", "genre");

        List<Movie> movieList = movieService.getMoviesByYearOfRelease(10);
        long id = movieList.get(0).getId();

        boolean created = reviewService.createReview(id, username, "Denne filmen var knallgod!", 4L);

        assertTrue(created);
    }

    @Test
    public void shouldGetAllReviews() {
        createAMovieAndSomeReviews();

        List<Review> list = reviewService.getAllReviews(10);

        assertEquals(5, list.size());
    }

    @Test
    public void shouldRetrieveReviewSortedByStars() {
        createAMovieAndSomeReviews();

        Long movieId = reviewService.getAllReviews(10).get(0).getReviewId().getMovie().getId();

        List<Review> reviews = reviewService.getReviewsForMovieByStars(movieId, 10);

        assertEquals(5, reviews.size());

        assertEquals(5, reviews.get(0).getStars());
        assertEquals(4, reviews.get(1).getStars());
        assertEquals(3, reviews.get(2).getStars());
        assertEquals(2, reviews.get(3).getStars());
        assertEquals(1, reviews.get(4).getStars());
    }

    @Test
    public void shouldRetrieveReviewsSortedByDate() {
        createAMovieAndSomeReviews();

        Long movieId = reviewService.getAllReviews(10).get(0).getReviewId().getMovie().getId();

        List<Review> reviews = reviewService.getReviewsForMovieByDate(movieId, 10);

        assertEquals(3, reviews.get(0).getStars());
        assertEquals(4, reviews.get(1).getStars());
        assertEquals(2, reviews.get(2).getStars());
        assertEquals(5, reviews.get(3).getStars());
        assertEquals(1, reviews.get(4).getStars());
    }

    @Test
    public void shouldGetAverageStarsOfMovie() {
        createAMovieAndSomeReviews();

        Long movieId = reviewService.getAllReviews(10).get(0).getReviewId().getMovie().getId();

        Double avgStars = movieService.getAverageStars(movieId);

        assertEquals(3, avgStars);
    }

    @Test
    public void shouldThrowErrorIfMovieDoesntExists() {
        Long movieId = 123L;

        assertThrows(IllegalArgumentException.class, () -> movieService.getAverageStars(movieId));
    }

    @Test
    public void shouldRetrieveAllMoviesWithAvgStars() {
        createAMovieAndSomeReviews();
        List<MovieWithAvgStars> list = movieService.getMoviesWithAvgStars(10);

        assertEquals(null, list.get(0).getAvgStars());
        assertEquals(3, list.get(1).getAvgStars());
    }

    @Test
    public void shouldBeAbleToCompareReviewIds() {
        User user = new User();
        user.setUsername("Robot9000");
        Movie movie = new Movie();
        Movie movie2 = new Movie();
        movie.setTitle("Cool film");
        movie2.setTitle("Bad film");


        ReviewId reviewId = new ReviewId();
        ReviewId reviewId2 = new ReviewId();
        reviewId.setUser(user);
        reviewId2.setUser(user);
        reviewId.setMovie(movie);
        reviewId2.setMovie(movie);

        assertTrue(reviewId.equals(reviewId2));

        reviewId2.setMovie(movie2);
        assertFalse(reviewId.equals(reviewId2));
    }
}
