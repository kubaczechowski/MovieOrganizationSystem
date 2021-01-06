package sample.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class WebBrowserController implements Initializable {
    @FXML private WebView webView;
    private  String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void openPage(){
        WebEngine webEngine = webView.getEngine();
        String google = "http://www.google.com/search?q=";
        webEngine.load(google+searchQuery);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
