package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.be.Movie;
import sample.gui.model.MovieModel;
import sample.gui.util.AlertDisplayer;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;

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
        if(!isNumeric(categories.getText()))
            alertDisplayer.displayAlert("rating not selected",
                    "please select a rating",
                    "", Alert.AlertType.WARNING);
        if(nameField.getText().isEmpty())
            alertDisplayer.displayAlert("name is not chosen",
                    "please insert a name of the movie",
                    "", Alert.AlertType.WARNING);
        if(filelink.getText().isEmpty())
            alertDisplayer.displayAlert("path is not chosen",
                    "please choose a  filepath",
                    "only mp4 and mpeg4 files", Alert.AlertType.WARNING);


        if(isNumeric(categories.getText())&& filelink.getText()!=null && nameField.getText()!=null) {
            boolean exists = movieModel.checkIfThisTitleExists(nameField.getText());
            if(exists)
                alertDisplayer.displayAlert("title",
                        "please select another title",
                        "this title exists in the db", Alert.AlertType.WARNING);
            else {
                //check if similar movie/movies is/are in DB
                List<String> namesOfSimilarMovies = movieModel.searchForSimilar(nameField.getText());
                //show information to the user
                if (namesOfSimilarMovies != null) {
                    String similar = " ";
                    for (String item : namesOfSimilarMovies) {
                        //I dont want to start with a comma not like this: ,item1,item2
                        //add comma if already there is an item
                        if (similar.length() > 1)
                            similar += ", ";

                        similar += item + " ";
                    }
                    boolean doYouWantToSave = alertDisplayer.displayConfirmationAlert("There are similar movies",
                            "Here are similar titles: " + similar, "if you want to add this movie press ok");
                    if (doYouWantToSave)
                        saveMovieToDB(actionEvent);
                } else {
                    saveMovieToDB(actionEvent);
                }
            }
        }
    }

    private void saveMovieToDB(ActionEvent actionEvent){
                movieModel.save(createObject());
                closeStage(actionEvent);
                //closing stage first we will make an impression that program
                //works faster than it actualy does
                movieModel.saveMovieInProgramFolder();
    }

    /**
     * method takes data from the user input and
     * makes a Movie object from that
     * @return Movie
     */
    private Movie createObject(){
            int id = -1;
            String name = nameField.getText();
            int rating = Integer.parseInt(categories.getText());
            int ratingIMDB = -1;
            String filelink = this.filelink.getText();
            Timestamp lasview = null;
            String imagePath = movieModel.setAndSaveImage(name);

            Movie movie = new Movie(id, name, rating, ratingIMDB, filelink, lasview, null, imagePath);
            return movie;
    }

    private  boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    /**
     * just close the scene if the button close is pressed
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }
    private void closeStage(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void choosFilelink(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
      String insertedRightFileExtension =
              movieModel.openFileChooser(n, nameField.getText());
      if(insertedRightFileExtension==null){
          alertDisplayer.displayAlert("adding filepath",
                  "please select file with .mp4 / .mpeg4 extension",
                  "incorrect extension", Alert.AlertType.WARNING);
      }
      else
          filelink.setText(insertedRightFileExtension);

    }

    public void setOne(ActionEvent actionEvent) {
        categories.setText("1");
    }

    public void setTwo(ActionEvent actionEvent) {
        categories.setText("2");
    }

    public void setThree(ActionEvent actionEvent) {
        categories.setText("3");
    }

    public void setFour(ActionEvent actionEvent) {
        categories.setText("4");
    }

    public void setFive(ActionEvent actionEvent) {
        categories.setText("5");
    }

    public void setSix(ActionEvent actionEvent) {
        categories.setText("6");
    }

    public void setSeven(ActionEvent actionEvent) {
        categories.setText("7");
    }

    public void setEight(ActionEvent actionEvent) {
        categories.setText("8");
    }

    public void setNine(ActionEvent actionEvent) {
        categories.setText("9");
    }

    public void setTen(ActionEvent actionEvent) {
        categories.setText("10");
    }
}
