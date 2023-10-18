package application.controller;

import database.DatabaseDictionary;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController extends MenuController implements Initializable {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private WebView webView = new WebView();

    @FXML
    private ContextMenu contextMenu = new ContextMenu();

    private WebEngine webEngine;
    public static DatabaseDictionary databaseDictionary = new DatabaseDictionary();

    public void search() {
        String word = searchField.getText();
        String definition = databaseDictionary.lookUpWord(word);
        webEngine.loadContent(definition);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadPage();
    }

    public void loadPage() {
        webEngine.loadContent("");
    }
}
