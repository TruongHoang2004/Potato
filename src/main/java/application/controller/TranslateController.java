package application.controller;

import database.TranslatorAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TranslateController extends MenuController {
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
            translatingLabel.setVisible(false);
        });
        task.setOnRunning(event -> translatingLabel.setVisible(true));
        new Thread(task).start();

    }
}
