package sample.bll;


import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;

import java.nio.file.Path;
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

    List<String> getSimilarCategories(String newCategory, List<Category> allCategories) throws BLLexception;

    boolean checkIfCategoryExists(String newCategory) throws BLLexception;

    List<Movie> searchMovies(String query) throws BLLexception;

    //cashe thingi
    void saveMovieToCache(Movie movie);
    List<Movie> getMoviesFromCashe();

    void deleteMovieFromCashe(Movie... selectedMovies);

    void deleteListOfMoviesFromCashe(List<Movie> moviesToDelete);

    void refreshcashList() throws BLLexception;

    void updateRating(Movie selectedMovie, int newRating) throws BLLexception;

    void updateSortingOption(boolean sortWithHigherRatings);

    void saveMovieInProgramFolder(Path destinationPath, Path originPath) throws BLLexception;

    String setAndSaveImage(String fieldname, Path destinationPath) throws BLLexception;

    boolean checkIfTitleExists(String text);
}
