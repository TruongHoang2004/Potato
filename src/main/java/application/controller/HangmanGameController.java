package application.controller;

import application.SceneManager;
import database.Tries;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

public class HangmanGameController extends GameMenuController implements Initializable {

    @FXML
    private HBox crossWord = new HBox(10);
    @FXML
    private Button restartButton = new Button();
    @FXML
    private ImageView hangmanImage = new ImageView();
    @FXML
    private Label resultLabel = new Label();
    private String answer;
    private int numOfFalse = 0;
    private String gameState = "playing";

    public void action(ActionEvent event) {
        if (gameState.equals("playing")) {
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
            hangmanImage.setImage(new Image(this.getClass().getResource("/application/image/hangman/" + this.numOfFalse + ".png").toString()));
        }
        if (this.numOfFalse == 9) {
            gameState = "breaking";
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    hangmanImage.setImage(new Image(this.getClass().getResource("/application/image/hangman/9.png").toString()));
                    Thread.sleep(500);
                    hangmanImage.setImage(new Image(this.getClass().getResource("/application/image/hangman/10.png").toString()));
                    return null;
                }
            };
            task.setOnSucceeded(event -> {
                resultLabel.setText(this.answer.toUpperCase());
            });
            new Thread(task).start();
        }

        if (this.crossWord.getChildren().stream().noneMatch(node -> ((Label) node).getText().equals("_"))) {

            hangmanImage.setImage(new Image(this.getClass().getResource("/application/image/hangman/11.png").toString()));
            resultLabel.setText("You win!");
            gameState = "breaking";
        }
    }

    public void restart(ActionEvent event) {

        if (gameState.equals("playing")) {
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
