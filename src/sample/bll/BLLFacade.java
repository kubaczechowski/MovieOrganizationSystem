package sample.bll;

import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;

import java.sql.Timestamp;
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

    List<Movie> getMoviesToDelete() throws BLLexception;

    void deleteCategoriesItemsAssociated(int id) throws BLLexception;

    void updateLastview(Movie movieToPlay) throws BLLexception;

    //timeconverter
    String timeDifference(int currentTimeInMillis,
                          int lastviewInMillis, Timestamp timestamp);
    List<String> getSimilarMovies(String newTitle) throws BLLexception;

    boolean checkIfCategoryExists(String newCategory) throws BLLexception;

    List<Movie> searchMovies(String query) throws BLLexception;

    void saveMovieToCache(Movie movie);
}
