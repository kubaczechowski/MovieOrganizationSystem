package sample.dal;

import sample.be.Category;
import sample.be.Movie;
import sample.dal.File.FilesOperations;
import sample.dal.cache.MovieCache;
import sample.dal.exception.DALexception;
import sample.dal.exception.FileExceptionDAL;
import sample.dal.exception.JCodecExceptionDAL;

import java.nio.file.Path;
import java.util.List;

public class DALController implements IDALFacade{
    private MovieDB movieDB = new MovieDB();
    private CategoryDB categoryDB = new CategoryDB();
    private CatMovieDAO catMovieDAO = new CatMovieDAO();
    private FilesOperations filesOperations = FilesOperations.getInstance();
    private MovieCache movieCache = MovieCache.getInstance();

    //Movie
    @Override
    public List<Movie> getAllMovies() throws DALexception {
       return movieDB.getAllMovies();
    }

    @Override
    public void addMovie(Movie movie) throws DALexception {
        movieDB.addMovie(movie);
    }

    @Override
    public void deleteMovie(Movie movie) throws DALexception {
        movieDB.deleteMovie(movie);
    }

    @Override
    public List<Movie> getMoviesToDelete() throws DALexception {
        return movieDB.getMoviesToDelete();
    }

    @Override
    public void updateLastViewFor(Movie movie) throws DALexception {
        movieDB.updateLastViewFor(movie);
    }

    @Override
    public void updateRating(Movie movie, int newRaing) throws DALexception {
        movieDB.updateRating(movie, newRaing);
    }

    //category
    @Override
    public List<Category> getAllCategories() throws DALexception {
        return categoryDB.getAllCategories();
    }

    @Override
    public void addCategory(Category category) throws DALexception {
        categoryDB.addCategory(category);
    }

    @Override
    public void deleteCategory(Category category) throws DALexception {
        categoryDB.deleteCategory(category);
    }

    @Override
    public void setAllMoviesInCache() throws DALexception {
        movieCache.setAllMovies(getAllMovies());
    }

    @Override
    public List<Movie> getAllMoviesFromCache() {
        return movieCache.getAllMovies();
    }

    @Override
    public void saveMoviesInCache(Movie movie) {
        movieCache.saveMovies(movie);
    }

    @Override
    public void deleteMovieFromCache(Movie[] selectedMovies) {
        movieCache.removeMovie(selectedMovies);
    }

    @Override
    public void deleteListOfMoviesFromCashe(List<Movie> moviesToDelete) {
        movieCache.removeListOfMovies(moviesToDelete);
    }

    @Override
    public void refreshCacheList() throws DALexception {
        movieCache.refresh(getAllMovies());
    }

    @Override
    public boolean checkIfSuchCategoryExists(String name) throws DALexception {
        return categoryDB.checkIfSuchCategoryExists(name);
    }

    @Override
    public List<Category> getCategoriesFromSpecificMovie(int movieID) throws DALexception {
        return catMovieDAO.getCategoriesFromSpecificMovie(movieID);
    }

    @Override
    public void addCategoryToMovie(int categoryID, int movieID) throws DALexception {
        catMovieDAO.addCategoryToMovie(categoryID, movieID);
    }

    @Override
    public void deleteCategoryFromMovie(int categoryID, int movieID) throws DALexception {
        catMovieDAO.deleteCategoryFromMovie(categoryID, movieID);
    }

    @Override
    public boolean checkIfMovieHasSuchCategory(int categoryID, int movieID) throws DALexception {
         return catMovieDAO.checkIfMovieHasSuchCategory(categoryID, movieID);
    }

    @Override
    public void deleteAllCategoriesForMovie(int movieID) throws DALexception {
        catMovieDAO.deleteAllCategoriesForMovie(movieID);
    }

    public void saveFileInProgramFolder(Path destinationPath, Path originPath) throws FileExceptionDAL {
        filesOperations.saveFileInProgramFolder(destinationPath, originPath);
    }

    @Override
    public String setAndSaveImage(String fieldname, Path destinationPath) throws FileExceptionDAL, JCodecExceptionDAL {
        return filesOperations.setAndSaveImage(fieldname, destinationPath);
    }
}
