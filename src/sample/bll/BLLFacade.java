package sample.bll;

import sample.be.Movie;
import sample.bll.exception.BLLexception;

import java.util.List;

public interface BLLFacade {
    List<Movie> getAllMovies() throws BLLexception;

    void deleteMovie(Movie selectedMovie) throws BLLexception;

    void saveMovie(Movie movie) throws BLLexception;
}
