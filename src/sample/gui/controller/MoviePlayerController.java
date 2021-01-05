package sample.gui.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.media.MediaView;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class MoviePlayerController implements Initializable {

    private static MoviePlayerController moviePlayerController;
    private String filePath;

   @FXML private MediaView mediaView;
   private Media media;
   private MediaPlayer mediaPlayer;

    public MoviePlayerController(String filelink) {
        this.filePath = filelink;
    }

    public MoviePlayerController() {
    }

    public static MoviePlayerController getInstance() {
        if(moviePlayerController ==  null)
            moviePlayerController = new MoviePlayerController();
        return moviePlayerController;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setProperties();
    }

    private void setProperties() {
        final DoubleProperty width = mediaView.fitWidthProperty();
        final DoubleProperty height = mediaView.fitHeightProperty();

        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
    }

    public void play(){
        if(getFilePath()==null)
            System.out.println("file path is null");
        //create media and media player
        Path path  = FileSystems.getDefault().getPath("src/../Movies/test.mp4");
        media = new Media(path.toUri().toString());
        mediaPlayer = new MediaPlayer(media);

        //set media player
        mediaView.setMediaPlayer(mediaPlayer);

      //play
        mediaPlayer.setAutoPlay(true);

    }

}
