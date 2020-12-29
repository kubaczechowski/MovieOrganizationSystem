package sample.bll;

import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;

import java.util.List;

public interface BLLFacade {
    List<Movie> getAllMovies() throws BLLexception;

    void deleteMovie(Movie selectedMovie) throws BLLexception;

    void saveMovie(Movie movie) throws BLLexception;

    void saveCategory(Category category) throws BLLexception;

    void deleteCategory(Category selectedItem) throws BLLexception;

    List<Category> getAllCategories() throws BLLexception;

    void addCategoryItem(int movieID, int categoryID) throws BLLexception;

    void deleteCategoryItem(int movieID, int categoryID) throws BLLexception;

    boolean checkIfMovieHasCategory(int movieID, int categoryID) throws BLLexception;
}
