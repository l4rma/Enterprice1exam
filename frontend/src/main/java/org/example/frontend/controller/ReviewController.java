package org.example.frontend.controller;

import org.example.backend.MovieWithAvgStars;
import org.example.backend.entity.Movie;
import org.example.backend.entity.Review;
import org.example.backend.service.MovieService;
import org.example.backend.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ReviewController implements Serializable {
    Logger log = LoggerFactory.getLogger(ReviewController.class);
    private String reviewText;
    private Long stars;
    private int mode = 0;


    @Autowired
    private ReviewService reviewService = new ReviewService();

    public List<Review> getReviews(Long movieId, int max) {
        if (mode == 0) {
            return reviewService.getReviewsForMovieByDate(movieId, max);
        } else {
            return reviewService.getReviewsForMovieByStars(movieId, max);
        }
    }

    public List<Review> getReviewsForMovieByDate(Long movieId, int max) {
        return reviewService.getReviewsForMovieByDate(movieId, max);
    }

    public List<Review> getReviewsForMovieByStars(Long movieId, int max) {
        return reviewService.getReviewsForMovieByStars(movieId, max);
    }

//    public String createReview() {
//        boolean created = false;
//        try {
//            created = reviewService.createReview(1L, "lars", reviewText, stars);
//            reviewText = "";
//            stars = null;
//        }catch (Exception e){
//            //nothing to do
//        }
//        if(created) {
//            return "/index.jsf?faces-redirect=true";
//        }
//        return "/index.jsf?faces-redirect=true&error=true";
//    }

    public String sortReviews(String movieId) {
        return "/moviedetails.jsf?faces-redirect=true&movieId=" + movieId;
    }

    public String createReview(String movieId, String username) {
        String returnUrl = "/moviedetails.jsf?faces-redirect=true&movieId=" + movieId;
        try {
            if (reviewText.isEmpty() || reviewText == null) {
                throw new IllegalArgumentException("Review text is empty");
            }
            reviewService.createReview(Long.parseLong(movieId), username, reviewText, stars);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            returnUrl += "&error=empty_text";
        } catch (Exception e) {
            log.error(e.getMessage());
            returnUrl += "&error=duplicate";
        }
        reviewText = "";
        stars = null;
        return returnUrl;
    }

//    public void createReview(Long movieId, String username) {
//        reviewService.createReview(movieId, username, reviewText, stars);
//        reviewText = "";
//        stars = null;
//    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Long getStars() {
        return stars;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}


