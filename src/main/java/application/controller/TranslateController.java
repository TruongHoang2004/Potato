package application.controller;

import database.TranslatorAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class TranslateController extends MenuController{
    @FXML
    private TextArea inputTextArea = new TextArea();
    @FXML
    private TextArea outputTextArea = new TextArea();
    @FXML
    private Button translateButtonViToEn = new Button();
    @FXML
    private Button translateButtonEnToVi = new Button();

    private int Mode = 0;

    public void getOutputEnToVi() {
        Mode = 0;
    }

    public void getOutputViToEn() {
        Mode = 1;
    }
    public void translate() {
        String input = inputTextArea.getText();
        if (Mode == 0) {
            String output = TranslatorAPI.translateEnToVi(input);
            outputTextArea.setText(output);
        }
        else {
            String output = TranslatorAPI.translateViToEn(input);
            outputTextArea.setText(output);
        }
    }
}
