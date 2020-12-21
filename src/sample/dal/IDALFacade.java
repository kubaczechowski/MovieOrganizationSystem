package sample.dal;

import sample.be.Movie;

import java.util.List;

public interface IDALFacade {
    //Movie
   List<Movie> getAllMovies();
    void addMovie(Movie movie);
    void deleteMovie(Movie movie);
}
