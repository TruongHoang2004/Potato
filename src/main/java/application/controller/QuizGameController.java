package application.controller;

import application.SceneManager;
import database.GameDatabase;
import database.Question;
import database.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class QuizGameController extends ControllerSwitcher implements Initializable {

    @FXML
    Text questionLabel;
    @FXML
    Button answer1Button = new Button();
    @FXML
    Button answer2Button = new Button();
    @FXML
    Button answer3Button = new Button();
    @FXML
    Button answer4Button = new Button();
    @FXML
    Button b1 = new Button("1");
    @FXML
    Button b2 = new Button("2");
    @FXML
    Button b3 = new Button("3");
    @FXML
    Button b4 = new Button("4");
    @FXML
    Button b5 = new Button("5");
    @FXML
    Button b6 = new Button("6");
    @FXML
    Button b7 = new Button("7");
    @FXML
    Button b8 = new Button("8");
    @FXML
    Button b9 = new Button("9");
    @FXML
    Button b10 = new Button("10");
    @FXML
    Button nextAndSubmitButton = new Button("Next");
    @FXML
    Label resultLabel = new Label();
    int currentQuestion = 0;
    Button currentButton = b1;
    boolean playing = true;
    int correctNumber = 0;


    private final List<Question> questions = new ArrayList<>();
    private final GameDatabase gameDatabase = new GameDatabase();

    public void loadAllQuestions() {
        List<String> topics = gameDatabase.getAllTopics();
        for (String topic : topics) {
            List<Word> words = gameDatabase.getWordsByTopic(topic);
            Random random = new Random();
            Word word = words.get(random.nextInt(words.size()));
            String[] answers = new String[4];
            int correctAnswer = random.nextInt(4);
            answers[correctAnswer] = word.getWordTarget();

            for (int i = 0; i < 4; i++) {
                if (i != correctAnswer) {
                    Word wrongWord = words.get(random.nextInt(words.size()));
                    if (Arrays.asList(answers).contains(wrongWord.getWordTarget())) {
                        i--;
                        continue;
                    }
                    answers[i] = wrongWord.getWordTarget();
                }
            }

            questions.add(new Question(word.getWordExplain(), answers, correctAnswer));
        }
    }

    public void loadQuestion(ActionEvent event) {
        currentQuestion = Integer.parseInt(((Button) event.getSource()).getText()) - 1;
        answer1Button.setStyle("-fx-background-color: #ffffff");
        answer2Button.setStyle("-fx-background-color: #ffffff");
        answer3Button.setStyle("-fx-background-color: #ffffff");
        answer4Button.setStyle("-fx-background-color: #ffffff");

        answer1Button.setText(questions.get(currentQuestion).getAnswer()[0]);
        answer2Button.setText(questions.get(currentQuestion).getAnswer()[1]);
        answer3Button.setText(questions.get(currentQuestion).getAnswer()[2]);
        answer4Button.setText(questions.get(currentQuestion).getAnswer()[3]);
        questionLabel.setText(questions.get(currentQuestion).getQuestion());

        if (playing) {
            if (currentQuestion == 9) {
                nextAndSubmitButton.setText("Submit");
                nextAndSubmitButton.setOnAction(event1 -> submit());
            } else {
                nextAndSubmitButton.setText("Next");
                nextAndSubmitButton.setOnAction(event1 -> next());
            }
           switch (questions.get(currentQuestion).getChosenAnswer()) {
               case 0 -> answer1Button.setStyle("-fx-background-color: #ffff00");
               case 1 -> answer2Button.setStyle("-fx-background-color: #ffff00");
               case 2 -> answer3Button.setStyle("-fx-background-color: #ffff00");
               case 3 -> answer4Button.setStyle("-fx-background-color: #ffff00");
           }
           if (questions.get(Integer.parseInt(currentButton.getText()) - 1).getChosenAnswer() != -1) {
               currentButton.setStyle("-fx-background-color: #7d7569");
           } else {
               currentButton.setStyle("-fx-background-color: #ffffff");
           }
           currentButton = (Button) event.getSource();
           currentButton.setStyle("-fx-background-color: #ffff00");
        } else {
            switch (questions.get(currentQuestion).getChosenAnswer()) {
                case 0 -> answer1Button.setStyle("-fx-background-color: #ff0000");
                case 1 -> answer2Button.setStyle("-fx-background-color: #ff0000");
                case 2 -> answer3Button.setStyle("-fx-background-color: #ff0000");
                case 3 -> answer4Button.setStyle("-fx-background-color: #ff0000");
            }

            switch (questions.get(currentQuestion).getCorrectAnswer()) {
                case 0 -> answer1Button.setStyle("-fx-background-color: #ffff00");
                case 1 -> answer2Button.setStyle("-fx-background-color: #ffff00");
                case 2 -> answer3Button.setStyle("-fx-background-color: #ffff00");
                case 3 -> answer4Button.setStyle("-fx-background-color: #ffff00");
            }
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllQuestions();

        answer1Button.setOnAction(event -> {
            if (playing) {
                questions.get(currentQuestion).setChosenAnswer(0);
                answer1Button.setStyle("-fx-background-color: #ffff00");
                answer2Button.setStyle("-fx-background-color: #ffffff");
                answer3Button.setStyle("-fx-background-color: #ffffff");
                answer4Button.setStyle("-fx-background-color: #ffffff");
            }
        });

        answer2Button.setOnAction(event -> {
            if (playing) {
                questions.get(currentQuestion).setChosenAnswer(1);
                answer1Button.setStyle("-fx-background-color: #ffffff");
                answer2Button.setStyle("-fx-background-color: #ffff00");
                answer3Button.setStyle("-fx-background-color: #ffffff");
                answer4Button.setStyle("-fx-background-color: #ffffff");
            }
        });

        answer3Button.setOnAction(event -> {
            if (playing) {
                questions.get(currentQuestion).setChosenAnswer(2);
                answer1Button.setStyle("-fx-background-color: #ffffff");
                answer2Button.setStyle("-fx-background-color: #ffffff");
                answer3Button.setStyle("-fx-background-color: #ffff00");
                answer4Button.setStyle("-fx-background-color: #ffffff");
            }
        });

        answer4Button.setOnAction(event -> {
            if (playing) {
                questions.get(currentQuestion).setChosenAnswer(3);
                answer1Button.setStyle("-fx-background-color: #ffffff");
                answer2Button.setStyle("-fx-background-color: #ffffff");
                answer3Button.setStyle("-fx-background-color: #ffffff");
                answer4Button.setStyle("-fx-background-color: #ffff00");
            }
        });

        b1.fire();
    }

    public void submit() {

        if (!playing) {
            return;
        }

        playing = false;
        for (int i = 0; i < 10; ++i) {
            if (questions.get(i).isCorrect()) {
                correctNumber++;
            }
        }

        if (questions.get(0).isCorrect()) {
            b1.setStyle("-fx-background-color: #ffff00");
        } else {
            b1.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(1).isCorrect()) {
            b2.setStyle("-fx-background-color: #ffff00");
        } else {
            b2.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(2).isCorrect()) {
            b3.setStyle("-fx-background-color: #ffff00");
        } else {
            b3.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(3).isCorrect()) {
            b4.setStyle("-fx-background-color: #ffff00");
        } else {
            b4.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(4).isCorrect()) {
            b5.setStyle("-fx-background-color: #ffff00");
        } else {
            b5.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(5).isCorrect()) {
            b6.setStyle("-fx-background-color: #ffff00");
        } else {
            b6.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(6).isCorrect()) {
            b7.setStyle("-fx-background-color: #ffff00");
        } else {
            b7.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(7).isCorrect()) {
            b8.setStyle("-fx-background-color: #ffff00");
        } else {
            b8.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(8).isCorrect()) {
            b9.setStyle("-fx-background-color: #ffff00");
        } else {
            b9.setStyle("-fx-background-color: #ff0000");
        }

        if (questions.get(9).isCorrect()) {
            b10.setStyle("-fx-background-color: #ffff00");
        } else {
            b10.setStyle("-fx-background-color: #ff0000");
        }

        resultLabel.setText("Correct: " + correctNumber + "/10");
    }

    public void next() {
        currentQuestion++;
        switch (currentQuestion - 1) {
            case 0 -> b2.fire();
            case 1 -> b3.fire();
            case 2 -> b4.fire();
            case 3 -> b5.fire();
            case 4 -> b6.fire();
            case 5 -> b7.fire();
            case 6 -> b8.fire();
            case 7 -> b9.fire();
            case 8 -> b10.fire();
            case 9 -> submit();
        }
    }

    public void playAgain(ActionEvent event) {
        if (playing) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Play again");
            dialog.setContentText("Do you want to play again?");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            if (dialog.showAndWait().get() == ButtonType.NO) {
                return;
            }
        }
        sceneManager.loadGame(SceneManager.SceneName.QUIZ_GAME, "view/QuizGame.fxml", event);
    }

    public void quit(ActionEvent event) {
        if (playing) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Quit");
            dialog.setContentText("Do you want to quit?");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            if (dialog.showAndWait().get() == ButtonType.NO) {
                return;
            }
        }
        switchToGameMenu(event);
    }
}