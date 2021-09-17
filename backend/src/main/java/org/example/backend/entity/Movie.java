package org.example.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Movie {

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 128;

    @Id
    @NotNull
    @GeneratedValue(generator = "entity_id_sequence")
    private Long id;

    @NotBlank
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    private String title;

    @NotNull
    @Min(1888)
    @Max(3000)
    private Long yearOfRelease;

    @NotBlank
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    private String director;

    @NotBlank
    @Size(min = MIN_SIZE, max = MAX_SIZE)
    private String genre;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Long yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
