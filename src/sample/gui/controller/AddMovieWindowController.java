package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.be.Movie;
import sample.gui.model.MovieModel;
import sample.gui.util.AlertDisplayer;

import java.io.File;
import java.nio.file.Path;
import java.sql.Timestamp;

public class AddMovieWindowController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField filelink;
    @FXML
    private MenuButton categories;

    private MovieModel movieModel = MovieModel.getInstance();
    private AlertDisplayer alertDisplayer = new AlertDisplayer();


    public void saveMovie(ActionEvent actionEvent) {
        movieModel.save(createObject());
    }

    private Movie createObject(){
        int id = -1;
        String name = nameField.getText();
        int rating = Integer.parseInt(categories.getText());
        int ratingIMDB = -1;
        String filelink = this.filelink.getText();
        Timestamp lasview = null;

        Movie movie = new Movie(id, name, rating ,ratingIMDB, filelink, lasview);
        return movie;
    }

    public void cancel(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void choosFilelink(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        Path pathOrigin = null;
        Path destinationPath = null;

        //open a file explorer
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        fileChooser.setTitle("Choose song");
        File file = fileChooser.showOpenDialog(stage);
        //create a copy of the chosen song in the application folder if the file was chosen
        //Only files ending with .mp4 or mpeg4 can be added
        if(file.getAbsolutePath().contains(".mp4") || file.getAbsolutePath().contains(".mpeg4"))
            pathOrigin = Path.of(file.getAbsolutePath());
        //if not show alert window
        else
            alertDisplayer.displayAlert("adding filepath",
                    "please select file with .mp4 / .mpeg4 extension",
                    "incorrect extension", Alert.AlertType.WARNING);
        //set destination path regarding the file extension
        if (pathOrigin.toString().contains(".mp4"))
            destinationPath = Path.of("../Movies/" + nameField.getText() + ".mp4" );
        else if(pathOrigin.toString().contains(".mpeg4"))
            destinationPath = Path.of("../Movies/" + nameField.getText() + ".mpeg4" );

        //show the destination path to the user
        filelink.setText(String.valueOf(destinationPath));
    }

    public void setOne(ActionEvent actionEvent) {
    }

    public void setTwo(ActionEvent actionEvent) {
    }

    public void setThree(ActionEvent actionEvent) {
    }

    public void setFour(ActionEvent actionEvent) {
    }

    public void setFive(ActionEvent actionEvent) {
    }

    public void setSix(ActionEvent actionEvent) {
    }

    public void setSeven(ActionEvent actionEvent) {
    }

    public void setEight(ActionEvent actionEvent) {
    }

    public void setNine(ActionEvent actionEvent) {
    }

    public void setTen(ActionEvent actionEvent) {
    }
}
