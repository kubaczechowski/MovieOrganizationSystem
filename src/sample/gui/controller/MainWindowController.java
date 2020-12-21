package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.be.Movie;
import sample.gui.model.MovieModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private MovieModel movieModel;

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
        //"/mytunes/gui/view/addMovieWindow.fxml"
        FXMLLoader loader = null;
        Parent root = null;
        createLoader(loader, "/mytunes/gui/view/addMovieWindow.fxml", root);
        // pass selected movie and songmodel
        AddMovieWindowController addMovieWindowController = loader.getController();
        addMovieWindowController.getData(movieModel, moviesTable.getSelectionModel().getSelectedItem());
        //create new window
        createStage(root, "New Movie");

    }

    private void createLoader(FXMLLoader loader, String path, Parent root)
    {
        loader = new FXMLLoader(getClass().getResource(path));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createStage(Parent root, String title)
    {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteMovieButton(ActionEvent actionEvent) {
    }

    public void changeRatingButton(ActionEvent actionEvent) {
    }
}
