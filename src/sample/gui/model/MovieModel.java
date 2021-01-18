package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.be.Movie;
import sample.bll.BLLController;
import sample.bll.BLLFacade;
import sample.bll.exception.BLLexception;
import sample.gui.util.AlertDisplayer;


import java.nio.file.Path;

import java.sql.Timestamp;
import java.util.Collections;

import java.util.List;

public class MovieModel {

    private static MovieModel movieModel;
    private ObservableList<Movie> obsMovies ;
    private BLLFacade logicLayer;
    private AlertDisplayer alertDisplayer = new AlertDisplayer();

    private MovieModel() {
        obsMovies = FXCollections.observableArrayList();

        try {
            logicLayer = new BLLController(true);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
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
        //delete from cashe
        logicLayer.deleteMovieFromCashe(selectedMovie);

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
        //save in the movieCache
        logicLayer.saveMovieToCache(movie);
    }

    public void updateLastview(Movie movieToPlay) {
        try {
            logicLayer.updateLastview(movieToPlay);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        logicLayer.saveMovieToCache(movieToPlay);
    }


    public String searchForSimilar(String text) {
        try {
            return logicLayer.getSimilarMovies(text);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        //if we get there its a bad sign
        return "problem happened in movie model";
    }

    /**
     * Method used for searching functionality
     * It refreshes the Observable List with the relevant titles
     * @param query
     */
    public void searchingFunctionality(String query ) {
        //delating text requires this
        if(query.isEmpty()) {
            obsMovies.clear();
                //get all movies from cashe
                obsMovies.addAll(logicLayer.getMoviesFromCashe());
        }
        else {
            List<Movie> searchResult = null;
            try {
                searchResult = logicLayer.searchMovies(query);
            } catch (BLLexception blLexception) {
                blLexception.printStackTrace();
            }
            obsMovies.clear();
            obsMovies.addAll(searchResult);
        }
    }

    public void updateRating(Movie selectedMovie, int newRating) {
        try {
            logicLayer.updateRating(selectedMovie, newRating);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
       /*
        obsMovies.clear();
        try {
            obsMovies.addAll(logicLayer.getAllMovies());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }

        */

        try {
            logicLayer.refreshcashList();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        obsMovies.clear();
        obsMovies.addAll(logicLayer.getMoviesFromCashe());
    }

    public void updateSortingOption(boolean sortWithHigherRatings) {
        logicLayer.updateSortingOption(sortWithHigherRatings);
    }

    public void saveMovieInProgramFolder(Path destinationPath, Path originPath) {
        try {
            logicLayer.saveMovieInProgramFolder(destinationPath, originPath);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public String setAndSaveImage(String fieldname, Path destinationPath) {
        try {
            return logicLayer.setAndSaveImage(fieldname, destinationPath);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return "something went wrong";
    }

    public boolean checkIfThisTitleExists(String text) {
        return logicLayer.checkIfTitleExists(text);
    }

    //alert displayer
    public boolean displayConfirmationAlert(String title, String information, String header){
       return alertDisplayer.displayConfirmationAlert(title, information, header);
    }
    public void displayAlert(String title, String information, String header,
                             Alert.AlertType alertType){
        alertDisplayer.displayAlert(title, information, header, alertType);
    }
    public String ShowTextInputDialog(String title, String contentText, String header, String whatToInput){
        return alertDisplayer.ShowTextInputDialog(title, contentText, header, whatToInput);
    }

    public Path getDestinationPath(String namefieldText, Path originPath) {
        return  logicLayer.getDestinationPath(namefieldText, originPath);
    }

    public boolean isValidFileExtension(String absolutePath) {
        return logicLayer.isValidFileExtension(absolutePath);
    }

    public boolean isNumeric(String text) {
        return logicLayer.isNumeric(text);
    }

    public boolean isChosenCategory(String text) {
        return logicLayer.isChosenCategory(text);
    }

    public boolean isFileLinkCorrect(String text) {
        return logicLayer.isFileLinkCorrect(text);
    }

    public boolean isNameFieldCorrect(String text) {
        return logicLayer.isNameFieldCorrect(text);
    }

    public String lastviewToShow(Movie movie) {
        return logicLayer.lastViewToShow(movie);
    }

    public boolean checkBoundsOfRating(int parseInt) {
        return logicLayer.checkBoundsOfRating(parseInt);
    }
}
