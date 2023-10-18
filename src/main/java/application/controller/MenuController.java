package application.controller;

import application.SceneManager;
import javafx.event.ActionEvent;

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

    public void switchToEditor(ActionEvent event) {
        try {
            sceneManager.switchScene("editor", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
