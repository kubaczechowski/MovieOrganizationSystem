package sample.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import sample.gui.model.CategoryModel;

import java.io.IOException;
import java.net.MalformedURLException;
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
        mediaView.setMediaPlayer(mediaPlayer);
    }

    public void play(){
        if(getFilePath()==null)
            System.out.println("file path is null");
        Path path  = FileSystems.getDefault().getPath(getFilePath());
        System.out.println(getFilePath());
        media = new Media(path.toUri().toString());
       // String path = getClass().getResource(getFilePath()).toExternalForm();
        //media = new Media(getFilePath());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public void open() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        String path = "/sample/gui/view/moviePlayer.fxml";
        fxmlLoader.setLocation(getClass().getResource(path));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    }

    private void createStage(Parent root, String title)
    {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
