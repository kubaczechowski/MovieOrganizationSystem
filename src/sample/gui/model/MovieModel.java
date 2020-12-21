package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.dal.DALController;
import sample.dal.MovieDB;

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
        obsMovies.addAll(logicLayer.getAllMovies());
    }

    public ObservableList<Movie> getAllMovies() {
        return obsMovies;
    }
}
