package application.controller;

import application.Switcher;
import javafx.event.ActionEvent;

public class MenuController {
    Switcher switcher = new Switcher();

    public void switchToDictionary(ActionEvent event) {
        try {
            switcher.switchScene("dictionary", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void switchToLearning(ActionEvent event) {
        try {
            switcher.switchScene("learning", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToGameMenu(ActionEvent event) {
        try {
            switcher.switchScene("gameMenu", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
