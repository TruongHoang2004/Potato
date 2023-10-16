package application.controller;

import database.TranslatorAPI;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TranslateController extends MenuController{
    @FXML
    private TextArea inputTextArea = new TextArea();
    @FXML
    private TextArea outputTextArea = new TextArea();

    public void translate() {
        String input = inputTextArea.getText();
        String output = TranslatorAPI.translateViToEn(input);
        outputTextArea.setText(output);
    }
}
