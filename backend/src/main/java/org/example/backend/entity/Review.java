package org.example.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
public class Review {

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 128;

    @EmbeddedId
    private ReviewId reviewId;

    @NotBlank
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    private String reviewText;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @NotNull
    @Min(1)
    @Max(5)
    private Long stars;

    public Review() {
        reviewId = new ReviewId();
    }

    public ReviewId getReviewId() {
        return reviewId;
    }

    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getStars() {
        return stars;
    }

    public void setStars(Long stars) {
        this.stars = stars;
    }

}
