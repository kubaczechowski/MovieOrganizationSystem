package sample.dal;

import sample.be.Movie;
import sample.dal.exception.DALexception;
import sample.dal.interfaces.CategoryInterface;
import sample.dal.interfaces.MovieInterface;

import java.util.List;

public interface IDALFacade extends MovieInterface, CategoryInterface {
    //Movie
   List<Movie> getAllMovies() throws DALexception;
    void addMovie(Movie movie) throws DALexception;
    void deleteMovie(Movie movie) throws DALexception;
}
