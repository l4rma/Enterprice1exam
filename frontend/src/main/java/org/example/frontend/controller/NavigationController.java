package org.example.frontend.controller;



import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class NavigationController implements Serializable {

    private Long currentMovieId = 1L;


    public String redirectToMovieDetails(Long movieId) {
        return "moviedetails?movieId="+movieId;
    }

    public Long getCurrentMovieId() {
        return currentMovieId;
    }

    public void setCurrentMovieId(Long currentMovieId) {
        this.currentMovieId = currentMovieId;
    }
}
