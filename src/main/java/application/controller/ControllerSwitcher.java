package application.controller;

import application.SceneManager;
import javafx.event.ActionEvent;

public class ControllerSwitcher {

    protected static final SceneManager sceneManager = new SceneManager();

    public void switchToDictionary(ActionEvent event) {
        try {
            sceneManager.switchScene(SceneManager.SceneName.DICTIONARY, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void switchToTranslator(ActionEvent event) {
        try {
            sceneManager.switchScene(SceneManager.SceneName.TRANSLATOR, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToGameMenu(ActionEvent event) {
        try {
            sceneManager.switchScene(SceneManager.SceneName.GAME_MENU, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToHangManGame(ActionEvent event) {
        try {
            sceneManager.switchScene(SceneManager.SceneName.HANGMAN_GAME_MENU, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToMenu(ActionEvent event) {
        try {
            new SceneManager().switchScene(SceneManager.SceneName.MENU, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToQuizGame(ActionEvent event) {
        try {
            new SceneManager().switchScene(SceneManager.SceneName.QUIZ_GAME, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
