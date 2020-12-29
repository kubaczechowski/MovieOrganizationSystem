package sample.dal;

import sample.be.Category;
import sample.be.Movie;
import sample.dal.exception.DALexception;
import sample.dal.interfaces.CatMovieInterface;
import sample.dal.interfaces.CategoryInterface;
import sample.dal.interfaces.MovieInterface;

import java.util.List;

public interface IDALFacade extends MovieInterface, CategoryInterface, CatMovieInterface {
    //Movie
   List<Movie> getAllMovies() throws DALexception;
    void addMovie(Movie movie) throws DALexception;
    void deleteMovie(Movie movie) throws DALexception;

    @Override
    List<Category> getAllCategories() throws DALexception;

    @Override
    void addCategory(Category category) throws DALexception;

    @Override
    void deleteCategory(Category category) throws DALexception;
}
