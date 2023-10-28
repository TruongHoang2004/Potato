package application.controller;

import database.TranslatorAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TranslateController extends MenuController implements Runnable {
    @FXML
    private TextArea inputTextArea = new TextArea();
    @FXML
    private TextArea outputTextArea = new TextArea();
    @FXML
    private Label upperLabel = new Label("English");
    @FXML
    private Label bottomLabel = new Label("Vietnamese");
    private Thread translate;

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
    }

    public void translate() {
        translate = new Thread(this);
        translate.start();
    }

    @Override
    public void run() {
        String input = inputTextArea.getText();
        if (mode == 0) {
            outputTextArea.setText(TranslatorAPI.translateEnToVi(input));
        } else {
            outputTextArea.setText(TranslatorAPI.translateViToEn(input));
        }
    }
}
