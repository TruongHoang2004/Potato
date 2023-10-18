package application.controller;

import database.DatabaseDictionary;
import database.Tries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
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
    HTMLEditor htmlEditor = new HTMLEditor();
    @FXML
    Pane editPane = new Pane();
    @FXML
    ListView<String> proposeWordList = new ListView<>();

    private WebEngine webEngine;
    private String mode = "search";
    public static DatabaseDictionary databaseDictionary = new DatabaseDictionary();

    public void searchBarAction() {
        if (mode.equals("search")) {
            String word = searchField.getText();
            String definition = databaseDictionary.lookUpWord(word);
            webEngine.loadContent(definition);
            proposeWordListAction(word);
        }
    }

    private void proposeWordListAction(String word) {
        proposeWordList.getItems().clear();
        proposeWordList.getItems().addAll(Tries.getWordSub(word));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadPage();

        proposeWordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            StringBuilder stringBuilder = new StringBuilder(newValue);
            searchField.setText(stringBuilder.toString());
            searchBarAction();
        });
    }

    public void loadPage() {
        webEngine.loadContent("");
    }

    public void switchToAddMode() {
        mode = "add";
        webView.setVisible(false);
        editPane.setVisible(true);
    }

    public void switchToEditMode() {
        mode = "edit";
        webView.setVisible(false);
        editPane.setVisible(true);
    }

    public void switchToSearch() {
        mode = "search";
        webView.setVisible(true);
        editPane.setVisible(false);
    }

    public void okButton() {
        if (mode.equals("add")) {
            databaseDictionary.addWord(searchField.getText(), htmlEditor.getHtmlText());
        } else if (mode.equals("edit")) {
            databaseDictionary.editWord(searchField.getText(), htmlEditor.getHtmlText());
        }
    }

    public void editWord() {
        if (mode.equals("edit")) {
            databaseDictionary.editWord(searchField.getText(), htmlEditor.getHtmlText());
        } else if (mode.equals("add")) {
            databaseDictionary.addWord(searchField.getText(), htmlEditor.getHtmlText());
        }

        proposeWordList.getItems().clear();
    }
}
