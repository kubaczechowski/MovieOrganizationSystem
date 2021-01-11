package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;


import java.awt.*;
import java.util.ArrayList;

import java.sql.Timestamp;
import java.util.Collections;

import java.util.List;

public class MovieModel {

    private static MovieModel movieModel;
    private ObservableList<Movie> obsMovies ;
    private BLLFacade logicLayer;

    private MovieModel() {
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

/*
    public ObservableList<Movie> searchMoviesByTitle(String text){
        List<Movie> movies = new ArrayList<>();

        for (Movie m:obsMovies)
        {
            if (m.getName().toUpperCase().contains(text.toUpperCase()) || String.valueOf(m.getRating()).contains(text))
                movies.add(m);
        }
        return FXCollections.observableArrayList(movies);

    }

 */



    public void updateLastview(Movie movieToPlay) {
        try {
            logicLayer.updateLastview(movieToPlay);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public String timeDifference(int currentTimeInMillis, int lastviewInMillis, Timestamp lastview) {
       return logicLayer.timeDifference(currentTimeInMillis, lastviewInMillis, lastview);
    }

    public List<String> searchForSimilar(String text) {
        try {
            return logicLayer.getSimilarMovies(text);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        //if we get there its a bad sign
        return Collections.singletonList("problem happened in movie model");
    }

    /**
     * Method used for searching functionality
     * It refreshes the Observable List with the relevant titles
     * @param query
     */
    public void searchingFunctionality(String query ) {
        if(query==null || query==" ")
        {
            obsMovies.clear();
            obsMovies.addAll(getAllMovies());
        }

        else {

            List<Movie> searchResult = null;
            try {
                searchResult = logicLayer.searchMovies(query);
            } catch (BLLexception blLexception) {
                blLexception.printStackTrace();
            }
            obsMovies.clear();
            if (searchResult != null)
                obsMovies.addAll(searchResult);
        }
    }
}
