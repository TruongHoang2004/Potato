package application.controller;

import application.SceneManager;
import database.Tries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class HangmanGameController extends GameMenuController implements Initializable {

    @FXML
    private HBox crossWord = new HBox(10);
    @FXML
    Label resultLabel = new Label("You win");
    @FXML
    Button restartButton = new Button();
    private String answer;
    private int numOfFalse = 0;
    private String gameStatus = "playing";

    public void action(ActionEvent event) {
        if (gameStatus.equals("playing")) {
            Button button = (Button) event.getSource();
            button.setVisible(false);
            check(button.getText().toLowerCase());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.answer = getRandomWord();
        for (int i = 0; i < this.answer.length(); i++) {
            Label letter = new Label("_");
            letter.setPrefWidth(30);
            letter.setFont(Font.font("System", FontWeight.BOLD, 30));
            this.crossWord.getChildren().add(letter);
        }
    }

    private String getRandomWord() {
        String word = Tries.getRamdomWord().getWordTarget();
        if (word.contains(" ")
                || word.contains("-")
                || word.contains("'")) {
            return getRandomWord();
        }
        return word;
    }

    private void check(String letter) {
        if (this.answer.contains(letter)) {
            for (int i = 0; i < this.answer.length(); i++) {
                if (this.answer.charAt(i) == letter.charAt(0)) {
                    ((Label) this.crossWord.getChildren().get(i)).setText(letter.toUpperCase());
                }
            }
        } else if (!letter.equals("a") && !letter.equals("e") && !letter.equals("i") && !letter.equals("o") && !letter.equals("u")) {
            this.numOfFalse++;
            System.out.println("false " + letter);
        }
        if (this.numOfFalse == 6) {
            Dialog dialog = new Dialog();
            dialog.setTitle("You lose");
            dialog.setContentText("The answer is " + this.answer + "\nDo you want to restart?");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            if (dialog.showAndWait().get().equals(ButtonType.YES)) {
                restartButton.fire();
            } else {
                gameStatus = "breaking";
            }
        }

        if (this.crossWord.getChildren().stream().noneMatch(node -> ((Label) node).getText().equals("_"))) {

            Dialog dialog = new Dialog();
            dialog.setTitle("You win");
            dialog.setContentText("Do you want to restart?");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            if (dialog.showAndWait().get().equals(ButtonType.YES)) {
                restartButton.fire();
            } else {
                gameStatus = "breaking";
            }
        }
    }

    public void restart(ActionEvent event) {

        if (gameStatus.equals("playing")) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Restart");
            dialog.setContentText("Are you sure you want to restart?");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            if (dialog.showAndWait().get().equals(ButtonType.YES)) {
                SceneManager sceneManager = new SceneManager();
                sceneManager.loadGame(SceneManager.SceneName.HANGMAN_GAME, "view/HangmanGame.fxml", event);
            }
        } else {
            SceneManager sceneManager = new SceneManager();
            sceneManager.loadGame(SceneManager.SceneName.HANGMAN_GAME, "view/HangmanGame.fxml", event);
        }
    }

    public void switchToGameMenu(ActionEvent event) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Back to game menu");
        dialog.setContentText("Are you sure you want to back to game menu?");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        if (dialog.showAndWait().get().equals(ButtonType.NO)) {
            return;
        }

        try {
            new SceneManager().switchScene(SceneManager.SceneName.GAME_MENU, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
