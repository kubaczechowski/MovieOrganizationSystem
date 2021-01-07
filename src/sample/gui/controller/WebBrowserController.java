package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class WebBrowserController implements Initializable {
    @FXML private WebView webView;
    @FXML private TextField searchField;
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
    }
}
