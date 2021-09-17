package org.example.backend.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewId implements Serializable {

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private User user;

    public ReviewId() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return movie.equals(reviewId.movie) &&
                user.equals(reviewId.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, user);
    }
}
