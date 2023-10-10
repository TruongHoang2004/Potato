package application.controller;

import application.Switcher;
import javafx.event.ActionEvent;

public class LearningController {
    public void back(ActionEvent event) {
        try {
            new Switcher().switchScene("menu", event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
