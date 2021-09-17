package org.example.backend;

import org.example.backend.entity.Movie;
import org.example.backend.entity.Review;
import org.example.backend.entity.ReviewId;
import org.example.backend.entity.User;
import org.junit.jupiter.api.Test;


import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class EntityTest {

    @Test
    public void shouldCreateUser() {
        User user = new User();
        user.setUsername("Person");
        user.setPassword("passord123");
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        assertEquals(user.getUsername(), "Person");
        assertEquals(user.getPassword(), "passord123");
        assertEquals(user.getRoles(), Collections.singleton("USER"));
        assertEquals(user.getEnabled(), true);
    }

    @Test
    public void shouldCreateMovie() {
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setYearOfRelease(2000L);
        movie.setDirector("director");
        movie.setGenre("genre");

        assertEquals("title", movie.getTitle());
        assertEquals(2000L, movie.getYearOfRelease());
        assertEquals("director", movie.getDirector());
        assertEquals("genre", movie.getGenre());
    }

    @Test
    public void shouldCreateReview() {
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setYearOfRelease(2000L);
        movie.setDirector("director");
        movie.setGenre("genre");
        movie.setId(1L);

        User user = new User();
        user.setUsername("Person");
        user.setPassword("passord123");

        Review review = new Review();
        ReviewId reviewId = new ReviewId();
        reviewId.setUser(user);
        reviewId.setMovie(movie);
        review.setReviewId(reviewId);
        review.setReviewText("reviewText");
        review.setStars(4L);
        int hash = review.hashCode();

        assertEquals(review.getReviewId().getMovie().getId(), 1L);
        assertEquals(review.getReviewId().getUser().getUsername(), "Person");
        assertEquals(review.getReviewText(), "reviewText");
        assertEquals(review.getStars(), 4L);
    }
}
