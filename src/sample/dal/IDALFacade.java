package sample.dal;

import sample.be.Category;
import sample.be.Movie;
import sample.dal.exception.DALexception;
import sample.dal.exception.FileExceptionDAL;
import sample.dal.interfaces.CatMovieInterface;
import sample.dal.interfaces.CategoryInterface;
import sample.dal.interfaces.FilesInterface;
import sample.dal.interfaces.MovieInterface;

import java.util.List;

public interface IDALFacade extends MovieInterface,
        CategoryInterface,  CatMovieInterface, FilesInterface {
    //Movie
   List<Movie> getAllMovies() throws DALexception;
    void addMovie(Movie movie) throws DALexception;
    void deleteMovie(Movie movie) throws DALexception, FileExceptionDAL;

    @Override
    List<Category> getAllCategories() throws DALexception;

    @Override
    void addCategory(Category category) throws DALexception;

    @Override
    void deleteCategory(Category category) throws DALexception;

    void setAllMoviesInCache() throws DALexception;

 List<Movie> getAllMoviesFromCache();

 void saveMoviesInCache(Movie movie);

 void deleteMovieFromCache(Movie[] selectedMovies);

 void deleteListOfMoviesFromCashe(List<Movie> moviesToDelete);

 void refreshCacheList() throws DALexception;
}
