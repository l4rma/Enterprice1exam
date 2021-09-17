package org.example.backend.service;

import org.example.backend.entity.Review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;



@Service
@Transactional
public class ReviewService {
    Logger log = LoggerFactory.getLogger(ReviewService.class);
    @Autowired
    private EntityManager em;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    public boolean createReview(@NotNull Long movieId, @NotBlank String username, @NotBlank String reviewText, @NotNull Long stars) {
        log.info(String.format("creating review: MovieId: %s\t username: %s\t reviewText: %s\t stars: %s",movieId,username,reviewText,stars));
        Review review = new Review();
        review.getReviewId().setMovie(movieService.getMovieById(movieId));
        review.getReviewId().setUser(userService.getUserByUsername(username));
        review.setReviewText(reviewText);
        review.setStars(stars);
        review.setDateCreated(new Date());

        em.persist(review);

        return true;
    }

    public List<Review> getAllReviews(int max) {
        Query query = em.createQuery("select r from Review r");
        query.setMaxResults(max);

        return query.getResultList();
    }

    public List<Review> getReviewsForMovieByStars(Long movieId, int max) {
        Query query = em.createQuery("select r from Review r WHERE r.reviewId.movie.id=?1 order by r.stars desc");
        query.setParameter(1, movieId);
        query.setMaxResults(max);

        return query.getResultList();
    }

    public List<Review> getReviewsForMovieByDate(Long movieId, int max) {
        Query query = em.createQuery("select r from Review r WHERE r.reviewId.movie.id=?1 order by r.dateCreated desc");
        query.setParameter(1, movieId);
        query.setMaxResults(max);

        return query.getResultList();
    }
}
