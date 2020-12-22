package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.be.Movie;
import sample.gui.model.MovieModel;
import sample.gui.util.AlertDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private MovieModel movieModel;
    private AlertDisplayer alertDisplayer = new AlertDisplayer();

    public MainWindowController() {
        movieModel = MovieModel.getInstance();

    }

    @FXML private TableView<Movie> moviesTable;
    @FXML private TableColumn<Movie, String> columnName;
    @FXML private TableColumn<Movie, Integer> columnRating;
    @FXML private TableColumn<Movie, String> columnLastView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
    }

    private void initTableView() {
        columnName.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        columnRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("rating"));
        columnLastView.setCellValueFactory(new PropertyValueFactory<Movie, String>("lastview"));
        movieModel.load();
        moviesTable.setItems(movieModel.getAllMovies());
    }


    public void addMovieButton(ActionEvent actionEvent) {
        //create loader
        FXMLLoader loader = null;
        Parent root = null;
        String path = "/sample/gui/view/addMovieWindow.fxml";
        loader = new FXMLLoader(getClass().getResource(path));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // pass selected movie and songmodel ; i think now its not needed but lets leave it
       // AddMovieWindowController addMovieWindowController = loader.getController();
       // addMovieWindowController.getData(movieModel);

        //create new window
        createStage(root, "New Movie");

    }

    private void createStage(Parent root, String title)
    {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteMovieButton(ActionEvent actionEvent) {
        Movie selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        //display alert if user didn't select movie
        if(selectedMovie==null)
            alertDisplayer.displayAlert("no movie",
                    "please select a movie", "no movie", Alert.AlertType.INFORMATION);

        //show alert to ensure that user wants to delete movie
         boolean result = alertDisplayer.displayConfirmationAlert("Delete Movie", "Do you want to delete movie?",
                "Delete");

         //delete movie if user decided to do so
         if(result==true) {
             //System.out.println("do action");
             //delete from table && db
             movieModel.delete(selectedMovie);
             movieModel.load();
         }
         //close the window if user decided not to delete movie
         else {
             //System.out.println("close the program");
             //overall do nothing??
         }
    }

    public void changeRatingButton(ActionEvent actionEvent) {
    }
}
