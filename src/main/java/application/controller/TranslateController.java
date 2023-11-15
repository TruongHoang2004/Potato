package application.controller;

import database.TextToSpeech;
import database.TranslatorAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class TranslateController extends ControllerSwitcher {

    @FXML
    private TextArea inputTextArea = new TextArea();
    @FXML
    private TextArea outputTextArea = new TextArea();
    @FXML
    private Label upperLabel = new Label("English");
    @FXML
    private Label bottomLabel = new Label("Vietnamese");
    @FXML
    private Label translatingLabel = new Label("Translating...");
    @FXML
    private ImageView loading = new ImageView();
    private int mode = 0;

    /**
     * Switch mode.
     * Mode = 0 is en to vi
     * Mode = 1 is vi to en
     */
    public void switchMode() {
        if (mode == 0) {
            mode = 1;
            upperLabel.setText("Vietnamese");
            bottomLabel.setText("English");
        } else {
            mode = 0;
            upperLabel.setText("English");
            bottomLabel.setText("Vietnamese");
        }

        String swap = inputTextArea.getText();
        inputTextArea.setText(outputTextArea.getText());
        outputTextArea.setText(swap);
    }

    public void translate() {
        String input = inputTextArea.getText();

        TranslatorAPI task;

        if (mode == 0) {
            task = new TranslatorAPI("en", "vi", input);
        } else {
            task = new TranslatorAPI("vi", "en", input);
        }

        task.setOnSucceeded(event -> {
            outputTextArea.setText(task.getValue());
            loading.setVisible(false);
        });

        task.setOnRunning(event -> {
            outputTextArea.setText("Translating...");
            loading.setVisible(true);
        });
        new Thread(task).start();
    }

    public void playSound1() {

        TextToSpeech task;
        if (mode == 0) {
            task = new TextToSpeech(inputTextArea.getText(), "en");
            new Thread(task).start();
        } else {
            task = new TextToSpeech(inputTextArea.getText(), "vi");
            new Thread(task).start();
        }

        task.setOnSucceeded(event -> {
            loading.setVisible(false);
        });

        task.setOnRunning(event -> {
            loading.setVisible(true);
        });

        new Thread(task).start();
    }

    public void playSound2() {
        TextToSpeech task;
        if (mode == 0) {
            task = new TextToSpeech(outputTextArea.getText(), "vi");
            new Thread(task).start();
        } else {
            task = new TextToSpeech(outputTextArea.getText(), "en");
            new Thread(task).start();
        }

        task.setOnSucceeded(event -> {
            loading.setVisible(false);
        });

        task.setOnRunning(event -> {
            loading.setVisible(true);
        });
    }

    public void setOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            translate();
        }
    }
}
