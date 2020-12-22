package sample.dal;

import sample.be.Movie;
import sample.dal.exception.DALexception;

import java.util.List;

public interface IDALFacade {
    //Movie
   List<Movie> getAllMovies() throws DALexception;
    void addMovie(Movie movie) throws DALexception;
    void deleteMovie(Movie movie) throws DALexception;
}
