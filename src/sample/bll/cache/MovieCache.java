package sample.bll.cache;

import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.exception.BLLexception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class MovieCache {
    private List<Movie> movies;
    private static MovieCache movieCache;

    private MovieCache() {

    }

    public static MovieCache getInstance() {
        if(movieCache==null){
            movieCache = new MovieCache();
        }
        return movieCache;
    }

    public void saveMovies(Movie... movies) {
        for (Movie movie : movies) {
            this.movies.removeIf(m -> m.getId() == movie.getId());
            this.movies.add(movie);
        }
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.movies = allMovies;
    }


    public void removeMovie(Movie movie) {
        movies.removeIf(m -> m.getId() == movie.getId());
    }


    public List<Movie> getAllMovies() {
        return movies;
    }
}
