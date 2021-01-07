package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovieModel {

    private static MovieModel movieModel;
    private ObservableList<Movie> obsMovies ;
    private BLLFacade logicLayer;

    public MovieModel() {
        obsMovies = FXCollections.observableArrayList();
        logicLayer = new BLLController();
    }

    public static MovieModel getInstance() {
        if(movieModel ==  null)
            movieModel = new MovieModel();
        
        return movieModel;
    }

    public void load() {
        obsMovies.clear();
        try {
            obsMovies.addAll(logicLayer.getAllMovies());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Movie> getAllMovies() {
        return obsMovies;
    }

    public void delete(Movie selectedMovie) {
        //delete from db
        try {
            logicLayer.deleteMovie(selectedMovie);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        //delete from tableview
        obsMovies.remove(selectedMovie);

    }

    public void save(Movie movie) {
        //save in DB
        try {
            logicLayer.saveMovie(movie);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        //save in tableview
        obsMovies.add(movie);
    }

    public ObservableList<Movie> searchMoviesByTitle(String text){
        List<Movie> movies = new ArrayList<>();

        for (Movie m:obsMovies)
        {
            if (m.getName().toUpperCase().contains(text.toUpperCase()) || String.valueOf(m.getRating()).contains(text))
                movies.add(m);
        }
        return FXCollections.observableArrayList(movies);

    }


}
