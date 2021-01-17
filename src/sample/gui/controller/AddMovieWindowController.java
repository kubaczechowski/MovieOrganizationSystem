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
import java.io.File;
import java.nio.file.Path;
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
   private Path destinationPath;
    private Path originPath;

    public void saveMovie(ActionEvent actionEvent) {
        checkUserInput();

        if(isNumeric(categories.getText()) && !filelink.getText().equals("file link") &&
                !filelink.getText().isEmpty()
                && nameField.getText()!=null && !nameField.getText().isEmpty()
        && !nameField.getText().equals("name")) {

            boolean exists = movieModel.checkIfThisTitleExists(nameField.getText());
            if(exists)
                movieModel.displayAlert("title",
                        "please select another title",
                        "this title exists in the db", Alert.AlertType.WARNING);
            else {
                //check if similar movie/movies is/are in DB
                List<String> namesOfSimilarMovies = movieModel.searchForSimilar(nameField.getText());
                //show information to the user
                if (namesOfSimilarMovies != null) {

                    boolean doYouWantToSave = movieModel.displayConfirmationAlert("There are similar movies",
                            "Here are similar titles: " + getListOfSimilarMoviesTitles(namesOfSimilarMovies),
                            "if you want to add this movie press ok");
                    if (doYouWantToSave)
                        saveMovieToDB(actionEvent);
                } else {
                    saveMovieToDB(actionEvent);
                }
            }
        }
    }

    private String getListOfSimilarMoviesTitles(List<String> namesOfSimilarMovies){
        String similar = " ";
        for (String item : namesOfSimilarMovies) {
            //I dont want to start with a comma not like this: ,item1,item2
            //add comma if already there is an item
            if (similar.length() > 1)
                similar += ", ";

            similar += item + " ";
        }
        return similar;
    }

    private void checkUserInput() {
        if(nameField.getText().isEmpty() || nameField.getText()==null ||
                nameField.getText().equals("name"))
            movieModel.displayAlert("name is not chosen",
                    "please insert a name of the movie",
                    "", Alert.AlertType.WARNING);
        if(!isNumeric(categories.getText()))
            movieModel.displayAlert("rating not selected",
                    "please select a rating",
                    "", Alert.AlertType.WARNING);
        //file link is the default text in the textfield
        if(destinationPath ==null)
            movieModel.displayAlert("path is not chosen xddddddd",
                    "please choose a  filepath",
                    "only mp4 and mpeg4 files", Alert.AlertType.WARNING);
    }

    private void saveMovieToDB(ActionEvent actionEvent){
                movieModel.saveMovieInProgramFolder(destinationPath, originPath);
        movieModel.save(createObject());
                closeStage(actionEvent);
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
            Timestamp lasview = null;
            String imagePath = movieModel.setAndSaveImage(name, Path.of(filelink.getText()));

            Movie movie = new Movie(id, name, rating, ratingIMDB, filelink.getText(), lasview, null, imagePath);
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
        //its destination path
      String insertedRightFileExtension =
              openFileChooser(n, nameField.getText());
      if(insertedRightFileExtension==null){
          movieModel.displayAlert("adding filepath",
                  "please select file with .mp4 / .mpeg4 extension",
                  "incorrect extension", Alert.AlertType.WARNING);
      }
      else {
          filelink.setText(insertedRightFileExtension);
      }
    }
    //boolean is for data validation
    //if true there was .mp4 / .mpeg4 file inserted
    public String openFileChooser(Node nodeOfTheScene, String namefieldText){
        FileChooser fileChooser = new FileChooser();

        //show fileChooser to the user && they decide which file
        File file = openFileChooserWindow(nodeOfTheScene, fileChooser);

        //validate data
        originPath = validateInput(file);

        //user inserted .mp4 / mpeg4
        if(originPath!=null) {
            destinationPath = getDestinationPath(namefieldText);
            if(destinationPath==null)
                movieModel.displayAlert("adding filepath",
                        "something went wrong",
                        "please try again", Alert.AlertType.ERROR);
            else
             return destinationPath.toString();
        }
            return null; // if we get there something went wrong
    }

    private Path validateInput(File file) {
        if(file.getAbsolutePath().contains(".mp4") || file.getAbsolutePath().contains(".mpeg4"))
            return  Path.of(file.getAbsolutePath());

        return null;
    }

    private File openFileChooserWindow(Node n, FileChooser fileChooser){
        Stage stage = (Stage) n.getScene().getWindow();
        fileChooser.setTitle("Choose song");
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }

    private Path getDestinationPath(String namefieldText){
        if (originPath.toString().contains(".mp4"))
            return destinationPath = Path.of("src/../Movies/" + namefieldText + ".mp4" );
        else if(originPath.toString().contains(".mpeg4"))
            return destinationPath = Path.of("src/../Movies/" + namefieldText + ".mpeg4" );
        else
            return null; //something went wrong. there is weird case when it happens
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
