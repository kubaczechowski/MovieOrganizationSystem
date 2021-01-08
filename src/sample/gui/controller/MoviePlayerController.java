package sample.gui.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class MoviePlayerController implements Initializable {

    private static MoviePlayerController moviePlayerController;
    private String filePath;

   @FXML private MediaView mediaView;
   @FXML private Text label;
   @FXML private Slider progressBar;
   @FXML private Slider volumeSlider;

   private Media media;
   private MediaPlayer mediaPlayer;
   private Boolean isLooping;


    public MoviePlayerController() {
    }

    public void sendMovieName(String name) {
        label.setText(name);
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

       //set width and height
       width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
       height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
    }

    public void play(){
        if(getFilePath()==null)
            System.out.println("file path is null");
        //create media and media player
        //Path path  = FileSystems.getDefault().getPath("src/../Movies/test.mp4");
        Path path  = FileSystems.getDefault().getPath(getFilePath());
        //for now it must be like that in case someone adds a movie i dont have
        media = new Media(path.toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        //idk
        mediaPlayer.setVolume(volumeSlider.getValue());

        //set media player
        mediaView.setMediaPlayer(mediaPlayer);

      //play
        mediaPlayer.setAutoPlay(true);
        //other actoion
        //volume slider
       setVolumeSlider();
        //prograss bar
       setProgressBar();
    }

    private void setVolumeSlider()
    {
        volumeSlider.setValue(mediaPlayer.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });
    }

    private void setProgressBar()
    {
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable,
                                javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        });
        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });
        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
    }

    public void playStopButton(ActionEvent actionEvent) {
        if(!isPaused())
            mediaPlayer.pause();
        else
            mediaPlayer.play();
    }

    private boolean isPaused(){
        return mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING;
    }

    public void fromTheBeginningButton(ActionEvent actionEvent) {
        mediaPlayer.seek(Duration.ZERO);
    }

    public void toTheEndButton(ActionEvent actionEvent) {
        mediaPlayer.seek(media.getDuration());
    }

    private Boolean checkIfIsLooping()
    {
        if(isLooping==true)
            return true;
        else
            return false;
    }
    //later will also change the color
    public void loopingButton(ActionEvent actionEvent) {
        if(checkIfIsLooping()==true)
            isLooping=false;
        else
            isLooping = true;
    }

}
