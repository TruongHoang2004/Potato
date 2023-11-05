package application.controller;

import database.TranslatorAPI;
import database.Tries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class HangmanGameController extends GameMenuController implements Initializable {

    private int numOfFalse = 0;
    @FXML
    private HBox crossWord = new HBox(10);
    @FXML
    Label resultLabel = new Label("You win");
    private String answer;

    public void action(ActionEvent event) {
        Button button = (Button) event.getSource();
        button.setVisible(false);
        check(button.getText().toLowerCase());
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
        Random random = new Random();
        int index = random.nextInt(Tries.searchWord.size() - 1);
        if (Tries.searchWord.get(index).contains(" ")
                || Tries.searchWord.get(index).contains("-")
                || Tries.searchWord.get(index).contains("'")) {
            return getRandomWord();
        }
        return Tries.searchWord.get(index);
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
            resultLabel.setText("You lose");
        }

        if (this.crossWord.getChildren().stream().noneMatch(node -> ((Label) node).getText().equals("_"))) {

            resultLabel.setText("You win");
            TranslatorAPI task = new TranslatorAPI("en", "vi", this.answer);
            task.setOnSucceeded(event -> {
                System.out.println(this.answer + " : " + task.getValue());
            });
            task.setOnRunning(event -> {
                System.out.println("translating");
            });
            new Thread(task).start();
        }
    }
}
