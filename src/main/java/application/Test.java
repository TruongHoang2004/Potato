package application;

import javafx.application.Application;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(okButtonType);
        boolean disabled = false; // computed based on content of text fields, for example
        dialog.setTitle("Result");
        dialog.setContentText("LOLLLLLLLLLLLLLLLL");
        dialog.getDialogPane().lookupButton(okButtonType).setDisable(disabled);
        dialog.show();
    }
}
