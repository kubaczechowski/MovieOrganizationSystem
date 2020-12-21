package sample.dal;

import sample.be.Movie;

import java.util.List;

public interface MovieInterface {
    List<Movie> getAllMovies();
    void addMovie(Movie movie);
    void deleteMovie(Movie movie);

}
