package application.controller;

import application.Switcher;
import javafx.event.ActionEvent;

public class GameMenuController {

    Switcher switcher = new Switcher();

    public void switchToDictionary(ActionEvent event) {
        try {
            switcher.switchScene("dictionary", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
