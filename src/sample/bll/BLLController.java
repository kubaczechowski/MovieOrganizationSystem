package sample.bll;

import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;
import sample.bll.util.TimeCalculator;
import sample.dal.DALController;
import sample.dal.IDALFacade;
import sample.dal.exception.DALexception;

import java.sql.Timestamp;
import java.util.List;

public class BLLController implements BLLFacade{
    private IDALFacade dataaccess = new DALController();
    private TimeCalculator timeCalculator = new TimeCalculator();

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
            dataaccess.deleteMovie(selectedMovie);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("couldn't delete movie", daLexception);
        }
    }

    @Override
    public void saveMovie(Movie movie) throws BLLexception {
        try {
            dataaccess.addMovie(movie);
        } catch (DALexception daLexception) {
            //daLexception.printStackTrace();
            throw new BLLexception("Couldn't save movie", daLexception);
        }
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
    public boolean checkIfCategoryExists(String newCategory) throws BLLexception {
        try {
            return dataaccess.checkIfSuchCategoryExists(newCategory);
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't check if category exists", daLexception);
        }
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
