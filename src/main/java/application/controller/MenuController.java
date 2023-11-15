package application.controller;

import application.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class  MenuController extends ControllerSwitcher {

    public void quit(ActionEvent event) {
        Platform.exit();
    }
}
