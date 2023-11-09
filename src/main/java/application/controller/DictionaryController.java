package application.controller;

import database.DatabaseDictionary;
import database.Tries;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
    @FXML
    ListView<String> historyWordList = new ListView<>();
    @FXML
    private Label modeLabel = new Label();

    String pickingWord;

    private WebEngine webEngine;
    private String mode = "search";
    public static DatabaseDictionary databaseDictionary = new DatabaseDictionary();

    public void searchBarAction() {
        pickingWord = searchField.getText();

        if (mode.equals("search")) {
            String definition = databaseDictionary.lookUpWord(pickingWord);
            webEngine.loadContent(definition);
            proposeWordListAction(pickingWord);
        } else if (mode.equals("edit") || mode.equals("add")) {
            htmlEditor.setHtmlText(databaseDictionary.lookUpWord(pickingWord));
            proposeWordListAction(pickingWord);
        }
    }

    private void proposeWordListAction(String word) {
        proposeWordList.getItems().clear();
        proposeWordList.getItems().addAll(Tries.getWordSub(word));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modeLabel.setText("Meaning:");
        webEngine = webView.getEngine();
        loadPage();
        proposeWordList.getItems().addAll(databaseDictionary.getAllWordsTarget());

        proposeWordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pickingWord = newValue;
            webEngine.loadContent(databaseDictionary.lookUpWord(pickingWord));
            if (pickingWord != null ) {
                if (historyWordList.getItems().contains(pickingWord)) {
                    historyWordList.getItems().remove(pickingWord);
                }
                historyWordList.getItems().add(0, pickingWord);
            }
        });

        historyWordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pickingWord = newValue;
            webEngine.loadContent(databaseDictionary.lookUpWord(pickingWord));
        });
    }

    public void loadPage() {
        webEngine.loadContent("");
    }

    public void switchToAddMode() {
        mode = "add";
        modeLabel.setText("Adding");
        webView.setVisible(false);
        editPane.setVisible(true);
        searchBarAction();
    }

    public void switchToEditMode() {
        mode = "edit";
        modeLabel.setText("Editing");
        webView.setVisible(false);
        editPane.setVisible(true);
        searchBarAction();
    }

    public void switchToSearch() {
        mode = "search";
        modeLabel.setText("Meaning:");
        webView.setVisible(true);
        editPane.setVisible(false);
    }

    public void okButton() {
        String word = searchField.getText();

        if (mode.equals("add")) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add word");
            dialog.setContentText("Are you sure you want to add this word?");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            if (dialog.showAndWait().get().equals(ButtonType.YES) && !pickingWord.equals("")) {
                databaseDictionary.addWord(word, htmlEditor.getHtmlText());
                Tries.searchWord.add(word);
                Tries.insertWordIntoTries(word);
                proposeWordList.getItems().add(word);
                Collections.sort(proposeWordList.getItems());
            }
        } else if (mode.equals("edit")) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit word");
            dialog.setContentText("Are you sure you want to edit this word?");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            if (dialog.showAndWait().get().equals(ButtonType.YES) && !pickingWord.equals("")) {
                databaseDictionary.editWord(word, htmlEditor.getHtmlText());
                Tries.searchWord.remove(pickingWord);
                Tries.deleteWordFromTries(pickingWord);
                Tries.searchWord.add(word);
                Tries.insertWordIntoTries(word);
                proposeWordList.getItems().remove(pickingWord);
                proposeWordList.getItems().add(word);
                Collections.sort(proposeWordList.getItems());
            }
        }
    }

    public void deleteWord() {
        modeLabel.setText("Deleting");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete word");
        dialog.setContentText("Are you sure you want to delete this word?");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        if (dialog.showAndWait().get().equals(ButtonType.YES) && !pickingWord.equals("")) {
            databaseDictionary.deleteWord(pickingWord);
            Tries.searchWord.remove(pickingWord);
            Tries.deleteWordFromTries(pickingWord);
            proposeWordList.getItems().remove(pickingWord);
        }
    }
}
