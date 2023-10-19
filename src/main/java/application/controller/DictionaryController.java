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

import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Collections;
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
        } else if (mode.equals("edit")) {
            String word = searchField.getText();
            htmlEditor.setHtmlText(databaseDictionary.lookUpWord(word));
            proposeWordListAction(word);
        }
    }

    private void proposeWordListAction(String word) {
        proposeWordList.scrollTo(word);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webEngine = webView.getEngine();
        loadPage();
        proposeWordList.getItems().addAll(databaseDictionary.getAllWordsTarget());

        proposeWordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String word = newValue;
            searchField.setText(word);
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
        searchBarAction();

    }

    public void switchToSearch() {
        mode = "search";
        webView.setVisible(true);
        editPane.setVisible(false);
    }

    public void okButton() {
        String word = searchField.getText();

        if (mode.equals("add")) {
            databaseDictionary.addWord(word, htmlEditor.getHtmlText());
            Tries.searchWord.add(word);
            Tries.insertWordIntoTries(word);
            proposeWordList.getItems().add(word);
            Collections.sort(proposeWordList.getItems());
        } else if (mode.equals("edit")) {
            databaseDictionary.editWord(word, htmlEditor.getHtmlText());
        }
    }

    public void deleteWord() {
        String word = searchField.getText();

        if (!htmlEditor.getHtmlText().equals("")) {
            databaseDictionary.deleteWord(word);
            Tries.searchWord.remove(word);
            Tries.deleteWordFromTries(word);
            proposeWordList.getItems().remove(word);
        }
    }
}
