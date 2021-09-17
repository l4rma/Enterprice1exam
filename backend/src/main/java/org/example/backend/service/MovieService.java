package org.example.backend.service;

import org.example.backend.MovieWithAvgStars;
import org.example.backend.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private EntityManager em;

    public boolean createMovie(@NotNull String title, @NotNull Long yearOfRelease, @NotNull String director, @NotNull String genre) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYearOfRelease(yearOfRelease);
        movie.setDirector(director);
        movie.setGenre(genre);

        em.persist(movie);

        return true;
    }

    public Movie getMovieById(Long movieId) {
        Query query = em.createQuery("select m from Movie m WHERE m.id=?1");
        query.setParameter(1, movieId);

        Movie result = (Movie) query.getSingleResult();

        return result;
    }

    public List<Movie> getMovies(int max) {
        Query query = em.createQuery("select m from Movie m");
        query.setMaxResults(max);

        List<Movie> result = query.getResultList();

        return result;
    }

    public List<Movie> getMoviesByYearOfRelease(int max) {
        Query query = em.createQuery("select m from Movie m order by m.yearOfRelease desc");
        query.setMaxResults(max);

        List<Movie> result = query.getResultList();

        return result;
    }

    public List<MovieWithAvgStars> getMoviesWithAvgStars(int max) {
        Query query = em.createQuery("select m, avg(r.stars) from Movie m left join Review r on m.id = r.reviewId.movie.id group by m.id");
        query.setMaxResults(max);
        List<MovieWithAvgStars> list = new ArrayList<>();

        List<Object> result = query.getResultList();

        for(Object o : result) {
            Object[] obj = (Object[]) o;
            list.add(new MovieWithAvgStars((Movie) obj[0], (Double) obj[1]));
        }

        return list;
    }

    public Double getAverageStars(Long movieId) {
        Query query = em.createQuery("select avg(r.stars) from Review r WHERE r.reviewId.movie.id=?1");
        query.setParameter(1, movieId);

        Double result = (Double) query.getSingleResult();

        if(result == null) {
            throw new IllegalArgumentException("No such movie: " + movieId);
        }

        return result;
    }

    public void deleteMovie(long id) {
        Movie movie = em.find(Movie.class, id);
        if(movie != null) {
            em.remove(movie);
        }
    }
}
