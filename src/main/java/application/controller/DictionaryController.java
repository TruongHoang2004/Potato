package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class DictionaryController extends MenuController {

    @FXML
    private TextField searchField = new TextField();

    @FXML
    private TextArea resultArea = new TextArea();

    @FXML
    private TextArea recommendedArea = new TextArea();

    @FXML
    private AnchorPane resultAnchorPane = new AnchorPane();

    public void search() {
        resultArea.setText(searchField.getText());
    }
}
