package application.controller;

import application.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class MenuController {
    SceneManager sceneManager = new SceneManager();

    public void switchToDictionary(ActionEvent event) {
        try {
            sceneManager.switchScene("dictionary", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void switchToLearning(ActionEvent event) {
        try {
            sceneManager.switchScene("translate", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToGameMenu(ActionEvent event) {
        try {
            sceneManager.switchScene("gameMenu", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent event) {
        try {
            new SceneManager().switchScene("menu", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
