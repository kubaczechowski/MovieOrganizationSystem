package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
public static Stage stage;



    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("gui/view/mainWindow.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("gui/view/deleteUnwatchedMovies.fxml"));
        stage.setTitle("Movie Organisation System");
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(getClass().getResource("/sample/gui/css/mainStyle.css").toExternalForm());
        primaryStage.show();

        //create loader
        FXMLLoader loader = null;
        Parent root2 = null;
        String path = "gui/view/deleteUnwatchedMovies.fxml";
        // method returns a URL object or null if no resource with this name(path) is found.
        loader = new FXMLLoader(getClass().getResource(path));
        try {
            root2 = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create new window
        createStage(root2, "delete movies");
    }

    private void createStage(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
