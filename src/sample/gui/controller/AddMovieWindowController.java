package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import sample.be.Movie;
import sample.gui.model.MovieModel;
import sample.gui.util.AlertDisplayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.List;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;


import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
        //check if similar movie/movies is/are in DB
        List<String> namesOfSimilarMovies = movieModel.searchForSimilar(nameField.getText());
        //show information to the user
        if(namesOfSimilarMovies!=null){
            String similar = " ";
            for(String item: namesOfSimilarMovies)
            {
                //I dont want to start with a comma not like this: ,item1,item2
                //add comma if already there is an item
                if(similar.length()>1)
                    similar+= ", ";

                similar +=  item+ " ";
            }
           boolean doYouWantToSave = alertDisplayer.displayConfirmationAlert("There are similar movies",
                   "Here are similar titles: " + similar, "if you want to add this movie press ok"  );
            if(doYouWantToSave)
                saveMovieToDB(actionEvent);
        }
        else {
           saveMovieToDB(actionEvent);
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

        Movie movie = new Movie(id, name,  rating ,ratingIMDB, filelink, lasview, null, imagePath);
        return movie;
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
      boolean insertedRightFileExtension =
              movieModel.openFileChooser(n, nameField.getText());
      if(insertedRightFileExtension){
          alertDisplayer.displayAlert("adding filepath",
                  "please select file with .mp4 / .mpeg4 extension",
                  "incorrect extension", Alert.AlertType.WARNING);
      }

    }
   // public void choosFilelink(ActionEvent actionEvent) {
       /* FileChooser fileChooser = new FileChooser();
        Path pathOrigin = null;
        Path destinationPath = null;

        //open a file explorer
        //I guess that a button presses is a parent of node
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
            destinationPath = Path.of("src/../Movies/" + nameField.getText() + ".mp4" );
        else if(pathOrigin.toString().contains(".mpeg4"))
            destinationPath = Path.of("/Movies/" + nameField.getText() + ".mpeg4" );

        //show the destination path to the user
        filelink.setText(String.valueOf(destinationPath));


        //create a copy in the application folder
        try {
            Files.copy(pathOrigin, destinationPath, REPLACE_EXISTING);// then lateer add replace existing
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file1 = new File(destinationPath.toString());
        //add image for a movie
        Picture frame = null;
        try {
            frame =  FrameGrab.getFrameFromFile(file1, 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JCodecException e) {
            e.printStackTrace();
        }

        BufferedImage bufferedImage = AWTUtil.toBufferedImage(frame);
        String filename = null;
        if(nameField==null) {
            long time = System.currentTimeMillis();
            filename = String.valueOf(time);
        }
        else
            filename = nameField.getText();
        try {
            ImageIO.write( bufferedImage, "jpg", new File("src/../Images/"+ filename +".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImagePath("src/../Images/" + filename+ ".jpg" );

        */
   // }

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
