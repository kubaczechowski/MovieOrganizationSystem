package sample.dal;

import sample.be.Category;
import sample.be.Movie;
import sample.dal.exception.DALexception;

import java.util.List;

public class DALController implements IDALFacade{
    private MovieDB movieDB = new MovieDB();
    private CategoryDB categoryDB = new CategoryDB();

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
}
