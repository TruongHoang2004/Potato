package application.controller;

import database.DatabaseDictionary;
import database.TextToSpeech;
import database.Tries;
import database.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class DictionaryController extends ControllerSwitcher implements Initializable {

    @FXML
    private TextField searchField = new TextField();
    @FXML
    private WebView webView = new WebView();
    @FXML
    HTMLEditor htmlEditor = new HTMLEditor();
    @FXML
    AnchorPane editPane = new AnchorPane();
    @FXML
    ListView<String> proposeWordList = new ListView<>();
    @FXML
    ListView<String> historyWordList = new ListView<>();
    @FXML
    private Label modeLabel = new Label();
    @FXML
    private Button cancelButton = new Button();
    @FXML
    private  Button okButton = new Button();
    String pickingWord;

    private WebEngine webEngine;
    private String mode = "search";
    public static DatabaseDictionary databaseDictionary = new DatabaseDictionary();

    public void searchBarAction() {
        pickingWord = searchField.getText();

        if (mode.equals("search")) {
            String definition = Tries.wordDefinitionSearch(pickingWord);
            if (pickingWord.isEmpty()) {
                webEngine.loadContent("");
            } else if (!definition.equals("000")) {
                webEngine.loadContent(definition);
            } else {
                webEngine.loadContent("<h1>Word not found</h1>");
            }
            proposeWordListAction(pickingWord);
        } else if (mode.equals("edit") || mode.equals("add")) {
            htmlEditor.setHtmlText(Tries.wordDefinitionSearch(pickingWord));
            proposeWordListAction(pickingWord);
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
        proposeWordList.getItems().addAll(databaseDictionary.getAllWordsTarget());

        proposeWordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pickingWord = newValue;
            webEngine.loadContent(Tries.wordDefinitionSearch(pickingWord));
            if (pickingWord != null ) {
                historyWordList.getItems().remove(pickingWord);
                historyWordList.getItems().add(0, pickingWord);

                if (historyWordList.getItems().size() > 12) {
                    historyWordList.getItems().remove(12);
                }
            }
        });

        historyWordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pickingWord = newValue;
            webEngine.loadContent(Tries.wordDefinitionSearch(pickingWord));
        });
    }

    public void loadPage() {
        webEngine.loadContent("");
    }

    public void switchToAddMode() {
        mode = "add";
        modeLabel.setText("Adding");
        webView.setVisible(false);
        htmlEditor.setVisible(true);
        okButton.setVisible(true);
        cancelButton.setVisible(true);
        searchBarAction();
    }

    public void switchToEditMode() {
        mode = "edit";
        modeLabel.setText("Editing");
        webView.setVisible(false);
        htmlEditor.setVisible(true);
        okButton.setVisible(true);
        cancelButton.setVisible(true);
        searchBarAction();
    }

    public void switchToSearch() {
        mode = "search";
        modeLabel.setText("Meaning:");
        webView.setVisible(true);
        htmlEditor.setVisible(false);
        okButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    public void okButton() {
        String word = searchField.getText();

        if (mode.equals("add")) {
            if (Tries.wordDefinitionSearch(word).equals("000")) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Add word");
                dialog.setContentText("Are you sure you want to add this word?");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                if (dialog.showAndWait().get().equals(ButtonType.YES) && !pickingWord.isEmpty()) {
                    databaseDictionary.addWord(word, htmlEditor.getHtmlText());
                    Tries.addWord(new Word(word, htmlEditor.getHtmlText()));
                    proposeWordList.getItems().add(word);
                    Collections.sort(proposeWordList.getItems());
                }
            } else {
                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Duplicate word");
                dialog.setContentText("This word is already in the dictionary");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
            }
        } else if (mode.equals("edit")) {
            if (!Tries.wordDefinitionSearch(word).equals("000")) {
                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Edit word");
                dialog.setContentText("This word is not in the dictionary");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();
            }
            else {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Edit word");
                dialog.setContentText("Are you sure you want to edit this word?");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                if (dialog.showAndWait().get().equals(ButtonType.YES) && !pickingWord.isEmpty()) {
                    databaseDictionary.editWord(word, htmlEditor.getHtmlText());
                    Tries.editWord(new Word(word, htmlEditor.getHtmlText()));
                    Collections.sort(proposeWordList.getItems());
                }
            }
        }
    }

    public void deleteWord() {
        modeLabel.setText("Deleting");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete word");
        dialog.setContentText("Are you sure you want to delete this word?");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        if (dialog.showAndWait().get().equals(ButtonType.YES) && !pickingWord.isEmpty()) {
            databaseDictionary.deleteWord(pickingWord);
            Tries.deleteWord(pickingWord);
            proposeWordList.getItems().remove(pickingWord);
        }
    }

    public void playSound() {
        try {
            TextToSpeech task = new TextToSpeech(pickingWord, "en");
            task.setOnFailed(event -> {
                Dialog<Void> dialog = new Dialog<>();
                dialog.setTitle("Can't play sound");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            });

            new Thread(task).start();
        } catch (Exception e) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Can't play sound");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        }
    }
}
