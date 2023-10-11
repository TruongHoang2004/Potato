package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DictionaryController extends MenuController {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private TextArea resultArea = new TextArea();

    public void search() {
        resultArea.setText(searchField.getText());
    }
}
