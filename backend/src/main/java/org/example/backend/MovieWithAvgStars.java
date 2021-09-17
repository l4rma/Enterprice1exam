package org.example.backend;

import org.example.backend.entity.Movie;

public class MovieWithAvgStars {

    private Movie movie;

    private Double avgStars;

    public MovieWithAvgStars(Movie movie, Double avgStars) {
        this.movie = movie;
        this.avgStars = avgStars;
    }

    public Movie getMovie() {
        return movie;
    }

    public Double getAvgStars() {
        return avgStars;
    }
}
