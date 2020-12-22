package sample.dal;

import sample.be.Movie;
import sample.dal.exception.DALexception;

import java.util.List;

public class DALController implements IDALFacade{
    private MovieDB movieDB = new MovieDB();
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
}
