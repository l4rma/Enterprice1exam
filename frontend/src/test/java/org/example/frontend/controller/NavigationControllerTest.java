package org.example.frontend.controller;

import org.example.TestFrontendApplicationRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestFrontendApplicationRunner.class)
public class NavigationControllerTest {

    @Autowired
    NavigationController navigationController;

    @Test
    public void shouldSetMovieIdAsAParameter() {
        Long movieId = 3L;

        assertEquals("moviedetails?movieId=3",
                navigationController.redirectToMovieDetails(movieId));

        movieId = 2L;
        assertEquals("moviedetails?movieId=2",
                navigationController.redirectToMovieDetails(movieId));

        movieId = 7L;
        assertEquals("moviedetails?movieId=7",
                navigationController.redirectToMovieDetails(movieId));

    }
}
