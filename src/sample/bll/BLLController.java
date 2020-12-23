package sample.bll;

import sample.be.Category;
import sample.be.Movie;
import sample.bll.exception.BLLexception;
import sample.dal.DALController;
import sample.dal.IDALFacade;
import sample.dal.exception.DALexception;

import java.util.List;

public class BLLController implements BLLFacade{
    private IDALFacade dataaccess = new DALController();

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
}
