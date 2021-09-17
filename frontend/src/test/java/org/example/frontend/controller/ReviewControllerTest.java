package org.example.frontend.controller;

import org.example.TestFrontendApplicationRunner;
import org.example.backend.entity.Movie;
import org.example.backend.entity.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class ReviewControllerTest {

    Long movieId = 1L; // The Shawshank Redemption (id = 1);

    @Autowired
    ReviewController reviewController;

    @Autowired
    SignUpController signUpController;

    @Autowired
    UserInfoController userInfoController;

    @Autowired
    DeleterService deleterService;

    @Test
    public void shouldNotGetMoreThanMaxReview() {
        List<Review> list = reviewController.getReviewsForMovieByDate(movieId, 5);

        assertEquals(5, list.size());
    }

    @Test
    public void shouldSortReviewsByStars() {
        List<Review> list = reviewController.getReviewsForMovieByStars(movieId, 5);

        assertEquals(5, list.get(0).getStars());
        assertEquals(4, list.get(1).getStars());
        assertEquals(4, list.get(2).getStars());
        assertEquals(4, list.get(3).getStars());
        assertEquals(2, list.get(4).getStars());
    }

    @Test
    public void shouldCreateReview() {
        String username = "TestUser1";
        String password = "test123";
        String reviewText = "This is only a test";
        Long stars = 1L;

        signUpController.setUsername(username);
        signUpController.setPassword(password);
        signUpController.signUpUser();

        reviewController.setReviewText(reviewText);
        reviewController.setStars(stars);

        reviewController.createReview("2", username);

        List<Review> list = reviewController.getReviewsForMovieByDate(2L, 5);

        assertEquals(1, list.get(0).getStars());
        assertEquals("TestUser1", list.get(0).getReviewId().getUser().getUsername());
        assertEquals("This is only a test", list.get(0).getReviewText());
    }

    @Test
    public void shouldResetReviewTextAndStarsAfterCreatingAReview() {
        String username = "TestUser2";
        String password = "test123";
        String reviewText = "This is also a test";
        Long stars = 1L;

        signUpController.setUsername(username);
        signUpController.setPassword(password);
        signUpController.signUpUser();

        reviewController.setReviewText(reviewText);
        reviewController.setStars(stars);

        assertEquals(1L, reviewController.getStars());
        assertEquals("This is also a test", reviewController.getReviewText());

        reviewController.createReview("2", username);

        assertNull(reviewController.getStars());
        assertEquals("", reviewController.getReviewText());
    }

    @Test
    public void shouldNotCreateEmptyReviewCatchItAndShowError() {
        String username = "TestUser3";
        String password = "test123";
        String reviewText = "";
        Long stars = 1L;

        signUpController.setUsername(username);
        signUpController.setPassword(password);
        signUpController.signUpUser();

        reviewController.setReviewText(reviewText);
        reviewController.setStars(stars);

        assertEquals("/moviedetails.jsf?faces-redirect=true&movieId=3&error=empty_text", reviewController.createReview("3", username));
    }

    @Test
    public void shouldNotCreateDuplicateReviewCatchAndShowErrorParameter() {
        String username = "TestUser4";
        String password = "test123";
        String movieId = "4";
        String reviewText = "This it an automatic test set for failure";
        Long stars = 1L;

        signUpController.setUsername(username);
        signUpController.setPassword(password);
        signUpController.signUpUser();

        reviewController.setReviewText(reviewText);
        reviewController.setStars(stars);

        reviewController.createReview(movieId, username);

        reviewController.setReviewText(reviewText);
        reviewController.setStars(stars);

        assertEquals("/moviedetails.jsf?faces-redirect=true&movieId=4&error=duplicate", reviewController.createReview(movieId, username));

    }

    @Test
    public void shouldGetReviewsByDate() {
        Long movieId = 1L;
        reviewController.setMode(0);
        assertTrue(0 == reviewController.getMode());
        List<Review> list = reviewController.getReviews(movieId, 5);

        assertTrue(list.get(0).getDateCreated().after(list.get(1).getDateCreated()));
        assertTrue(list.get(1).getDateCreated().after(list.get(2).getDateCreated()));
        assertTrue(list.get(2).getDateCreated().after(list.get(3).getDateCreated()));
        assertTrue(list.get(3).getDateCreated().after(list.get(4).getDateCreated()));
    }

    @Test
    public void shouldGetReviewsByStars() {
        Long movieId = 1L;
        reviewController.setMode(1);
        assertTrue(1 == reviewController.getMode());

        List<Review> list = reviewController.getReviews(movieId, 5);
        assertTrue(list.get(0).getStars()>=list.get(1).getStars());
        assertTrue(list.get(1).getStars()>=list.get(2).getStars());
        assertTrue(list.get(2).getStars()>=list.get(3).getStars());
        assertTrue(list.get(3).getStars()>=list.get(4).getStars());
    }
}
