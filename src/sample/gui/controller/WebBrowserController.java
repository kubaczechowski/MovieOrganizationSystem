package sample.gui.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.script.Bindings;
import java.net.URL;
import java.util.ResourceBundle;

public class WebBrowserController implements Initializable {
    @FXML private WebView webView;
    @FXML private TextField searchField;
    @FXML private StackPane stackPane;
    @FXML private AnchorPane anchorPane;
    private  String searchQuery;
    private String google = "http://www.google.com/search?q=";
    private String home = "https://www.google.com/webhp";
    private WebEngine webEngine;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void openPage(){
        //set textField
        searchField.setText(google+searchQuery);
       webEngine.load(google+searchQuery);
        makeResponsive();
    }


    private void makeResponsive() {
        //set fixed height to width ratio
        Stage stage = getStage();
        stage.minHeightProperty().bind(stage.widthProperty().multiply(0.75));
        stage.maxHeightProperty().bind(stage.widthProperty().multiply(0.75));

        //It doesn't work yet
        getStage().widthProperty().addListener((observableValue, number, t1)
                -> webView.setPrefWidth((double) t1));

        getStage().heightProperty().addListener((observableValue, number, t1)
                -> webView.setPrefHeight((double) t1));

    }

   private Stage getStage(){
        Stage stage = (Stage) stackPane.getScene().getWindow();
        return stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webView.setZoom(0.6);
        webEngine = webView.getEngine();
    }

    public void search(ActionEvent actionEvent) {
        String usersQuestion = searchField.getText();
        searchField.setText(google+usersQuestion);
        webEngine.load(google+usersQuestion);
    }

    public void home(ActionEvent actionEvent) {
        searchField.setText(home);
        webEngine.load(home);
    }

    public void fullScreen(ActionEvent actionEvent) {
        if(getStage().isFullScreen())
            getStage().setFullScreen(false);
        else {
            getStage().setFullScreen(true);
        }
    }
}
