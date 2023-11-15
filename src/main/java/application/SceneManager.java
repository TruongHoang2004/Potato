package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SceneManager {

    private static Stage stage = new Stage();
    private static Scene scene;
    private static final List<Scene> sceneList = new ArrayList<>();

    public void loadGame(SceneName name, String path, ActionEvent event) {
        try {
            sceneList.set(name.getIndex(), new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)))));
            switchScene(name, event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAll() {
        try {
            sceneList.add(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/Menu.fxml")))));
            sceneList.add(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/Dictionary.fxml")))));
            sceneList.add(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/Translator.fxml")))));
            sceneList.add(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/GameMenu.fxml")))));
            sceneList.add(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/HangmanGameMenu.fxml")))));
            sceneList.add(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/HangmanGame.fxml")))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void switchScene(SceneName name, ActionEvent event) throws Exception {

        double currentWith = stage.getWidth();
        double currentHeight = stage.getHeight();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = sceneList.get(name.getIndex());
        String css = Objects.requireNonNull(this.getClass().getResource("style/Style.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setWidth(currentWith);
        stage.setHeight(currentHeight);

        stage.setScene(scene);
        stage.show();
    }

    public enum SceneName {

        MENU(0),
        DICTIONARY(1),
        TRANSLATOR(2),
        GAME_MENU(3),
        HANGMAN_GAME_MENU(4),
        HANGMAN_GAME(5);
        private final int index;

        SceneName(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
}


