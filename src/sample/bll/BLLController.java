package sample.bll;


import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;
import sample.bll.util.MovieSearcher;
import sample.bll.util.SearchForSimilarTitles;
import sample.bll.util.TimeCalculator;
import sample.dal.DALController;
import sample.dal.IDALFacade;
import sample.dal.exception.DALexception;
import sample.dal.exception.FileExceptionDAL;
import sample.dal.exception.JCodecExceptionDAL;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;

public class BLLController implements BLLFacade{
    private IDALFacade dataaccess = new DALController();
    private TimeCalculator timeCalculator = new TimeCalculator();
    private SearchForSimilarTitles searchForSimilarTitles;
    private MovieSearcher movieSearcher = new MovieSearcher();



    //Initializer block. Called even before the constructor
    //used to initialize an instance variable
    {
        try {
            //is it updated each time we add new movie??
            searchForSimilarTitles = new SearchForSimilarTitles(getAllMovies());
        } catch (BLLexception blLexception) {
           // blLexception.printStackTrace();
            throw new BLLexception("couldnt search for similar titles", blLexception);
        }
    }

    public BLLController(boolean setCache) throws BLLexception {
        if(setCache) {
            try {
                dataaccess.setAllMoviesInCache();
            } catch (DALexception daLexception) {
                throw new BLLexception(" couldn't set all movies to the cashe", daLexception);
            }
        }
    }


    //Alternatively we could have written
    //method is final because calling non-final methods during instance initialization can cause problems
    /*
    private final SearchForSimilarTitles initializeInstanceVariable(){
        try {
           searchForSimilarTitles = new SearchForSimilarTitles(getAllMovies());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return searchForSimilarTitles;
    }

     */

    @Override
    public List<Movie> getAllMovies() throws BLLexception {
        try {
            return  dataaccess.getAllMovies();
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("couldn't get all movies", daLexception);
        }

    }

    @Override
    public void deleteMovie(Movie selectedMovie) throws BLLexception {
        try {
            dataaccess.deleteAllCategoriesForMovie(selectedMovie.getId());
            dataaccess.deleteMovie(selectedMovie);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("couldn't delete movie", daLexception);
        } catch (FileExceptionDAL fileExceptionDAL) {
            //fileExceptionDAL.printStackTrace();
            throw new BLLexception("couldn't delete movie", fileExceptionDAL);
        }
        searchForSimilarTitles.setAllMovies(getAllMovies());
    }

    @Override
    public void saveMovie(Movie movie) throws BLLexception {
        try {
            dataaccess.addMovie(movie);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("Couldn't save movie", daLexception);
        }
        searchForSimilarTitles.setAllMovies(getAllMovies());
    }

    @Override
    public void saveCategory(Category category) throws BLLexception {
        try {
            dataaccess.addCategory(category);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("Couldn't save category", daLexception);
        }
    }

    @Override
    public void deleteCategory(Category selectedItem) throws BLLexception {
        try {
            dataaccess.deleteCategory(selectedItem);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("Couldn't delete category", daLexception);
        }
    }

    @Override
    public List<Category> getAllCategories() throws BLLexception {
        try {
            return dataaccess.getAllCategories();
        } catch (DALexception daLexception) {
            ///daLexception.printStackTrace();
            throw new BLLexception("Couldn't get all categories", daLexception);
        }
    }

    @Override
    public void addCategoryItem(int movieID, int categoryID) throws BLLexception {
        try {
            dataaccess.addCategoryToMovie(categoryID, movieID);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't add category item", daLexception);
        }
    }

    @Override
    public void deleteCategoryItem(int movieID, int categoryID) throws BLLexception {
        try {
            dataaccess.deleteCategoryFromMovie(categoryID, movieID);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't delete category item", daLexception);
        }
    }

    @Override
    public void deleteCategoriesItemsAssociated(int movieID) throws BLLexception {
        try {
            dataaccess.deleteAllCategoriesForMovie(movieID);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("Couldn't delete categoires assocaited with the movie", daLexception);
        }
    }

    @Override
    public void updateLastview(Movie movieToPlay) throws BLLexception {
        try {
            dataaccess.updateLastViewFor(movieToPlay);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't update lastview", daLexception);
        }
    }

    //timeconverter
    @Override
    public String timeDifference(int currentTimeInMillis, int lastviewInMillis, Timestamp timestamp) {
        return timeCalculator.timeDifference(currentTimeInMillis, lastviewInMillis, timestamp);
    }

    @Override
    public List<String> getSimilarMovies(String newTitle) {
        return searchForSimilarTitles.getSimilarMovies(newTitle);
    }

    @Override
    public List<String> getSimilarCategories(String newCategory, List<Category> allCategories)  {
        return searchForSimilarTitles.getSimilarCategories(newCategory, allCategories);
    }

    @Override
    public boolean checkIfCategoryExists(String newCategory) throws BLLexception {
        try {
            return dataaccess.checkIfSuchCategoryExists(newCategory);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't check if category exists", daLexception);
        }
    }

    @Override
    public List<Movie> searchMovies(String query)  {
        //List<Movie> allMovies = getAllMovies();
       // List<Category> allCategories = getAllCategories();
        List<Movie> searchResult = movieSearcher.getSearch(dataaccess.getAllMoviesFromCache() , query);
        return searchResult;
    }
    // cashe class
    @Override
    public void saveMovieToCache(Movie movie) {
        dataaccess.saveMoviesInCache(movie);
    }

    @Override
    public List<Movie> getMoviesFromCashe() {
        return dataaccess.getAllMoviesFromCache();
    }

    @Override
    public void deleteMovieFromCashe(Movie... selectedMovies) {
        dataaccess.deleteMovieFromCache(selectedMovies);
    }

    @Override
    public void deleteListOfMoviesFromCashe(List<Movie> moviesToDelete) {
        dataaccess.deleteListOfMoviesFromCashe(moviesToDelete);
    }

    @Override
    public void refreshcashList() throws BLLexception {
        try {
            dataaccess.refreshCacheList();
        } catch (DALexception daLexception) {
           throw new BLLexception("Couldn't refresh cache list", daLexception);
        }
        // movieCache.refresh(getAllMovies());
    }

    @Override
    public void updateRating(Movie selectedMovie, int newRating) throws BLLexception {
        try {
            dataaccess.updateRating(selectedMovie, newRating);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't update the rating", daLexception);
        }
    }

    @Override
    public void updateSortingOption(boolean sortWithHigherRatings) {
        movieSearcher.setSortingOption(sortWithHigherRatings);
    }


    @Override
    public void saveMovieInProgramFolder(Path destinationPath, Path originPath) throws BLLexception {
        try {
            dataaccess.saveFileInProgramFolder(destinationPath, originPath);
        } catch (FileExceptionDAL fileExceptionDAL) {
            throw new BLLexception("Couldn;t save movie in program folder",
                    fileExceptionDAL);
        }
    }

    @Override
    public String setAndSaveImage(String fieldname, Path destinationPath) throws BLLexception {
        try {
            return dataaccess.setAndSaveImage(fieldname, destinationPath);
        } catch (FileExceptionDAL fileExceptionDAL) {
            throw new BLLexception("Couldn't save image and get filepath", fileExceptionDAL);
        } catch (JCodecExceptionDAL jCodecExceptionDAL) {
            throw new BLLexception("Couldn't save image and get filepath", jCodecExceptionDAL);
        }
    }

    @Override
    public boolean checkIfTitleExists(String text) {
        return searchForSimilarTitles.checkIfExists(text);
    }

    @Override
    public boolean checkIfMovieHasCategory(int movieID, int categoryID) throws BLLexception {
        try {
            return dataaccess.checkIfMovieHasSuchCategory(categoryID, movieID);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't check if movie has such category", daLexception);
        }
    }

    @Override
    public List<Movie> getMoviesToDelete() throws BLLexception {
        try {
            return dataaccess.getMoviesToDelete();
        } catch (DALexception daLexception) {
           // daLexception.printStackTrace();
            throw new BLLexception("Couldn't get movies to delete", daLexception);
        }
    }

}
