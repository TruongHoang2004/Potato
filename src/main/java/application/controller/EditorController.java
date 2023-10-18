package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;

public class EditorController extends MenuController {

    @FXML
    private Label titleLabel = new Label();
    @FXML
    private HTMLEditor editor = new HTMLEditor();

    public void pressApply() {

    }
}
