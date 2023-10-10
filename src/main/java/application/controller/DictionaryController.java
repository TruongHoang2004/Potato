package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DictionaryController {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private TextField resultField = new TextField();

    public void search() {
        resultField.setText(searchField.getText());
    }

    public void searchButtonOnAction() {
    }
}
