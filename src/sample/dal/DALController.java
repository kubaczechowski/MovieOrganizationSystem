package sample.dal;

import sample.be.Movie;

import java.util.List;

public class DALController implements IDALFacade{
    private MovieDB movieDB = new MovieDB();
    @Override
    public List<Movie> getAllMovies() {
       return movieDB.getAllMovies();
    }

    @Override
    public void addMovie(Movie movie) {

    }

    @Override
    public void deleteMovie(Movie movie) {

    }
}
