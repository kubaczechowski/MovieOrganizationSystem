package sample.dal.interfaces;

import sample.be.Movie;
import sample.dal.exception.DALexception;
import sample.dal.exception.FileExceptionDAL;

import java.util.List;

public interface MovieInterface {
    List<Movie> getAllMovies() throws DALexception;
    void addMovie(Movie movie) throws DALexception;
    void deleteMovie(Movie movie) throws DALexception, FileExceptionDAL;
    List<Movie> getMoviesToDelete() throws DALexception;
    void updateLastViewFor(Movie movie) throws DALexception;
    void updateRating(Movie movie, int newRaing)throws DALexception;

}
