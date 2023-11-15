package application.controller;

import database.GameDatabase;
import database.Question;
import database.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class QuizGameController implements Initializable {

    @FXML
    Text questionLabel;
    @FXML
    Button answer1Button;
    @FXML
    Button answer2Button;
    @FXML
    Button answer3Button;
    @FXML
    Button answer4Button;


    private List<Question> questions = new ArrayList<>();
    private GameDatabase gameDatabase = new GameDatabase();

    public void loadAllQuestions() {
        List<String> topics = gameDatabase.getAllTopics();
        for (String topic : topics) {
            List<Word> words = gameDatabase.getWordByTopic(topic);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllQuestions();
        Button button = new Button("1");
        loadQuestion(new ActionEvent(button, button));
    }

    public void loadQuestion(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = Integer.parseInt(button.getText()) - 1;
        Question question = questions.get(index);
        questionLabel.setText(question.getQuestion());
        answer1Button.setText(question.getAnswer()[0]);
        answer2Button.setText(question.getAnswer()[1]);
        answer3Button.setText(question.getAnswer()[2]);
        answer4Button.setText(question.getAnswer()[3]);
    }
}
