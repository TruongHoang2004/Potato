package application;

import application.controller.DictionaryController;
import database.Dictionary;
import database.GameDatabase;
import database.Tries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

import static database.Tries.words;

public class Main extends Application {

    public static void initialize() {
        try {
            System.out.println("Initialize application...");
            DictionaryController.databaseDictionary.initialize();
            GameDatabase.initialize();
            Tries.initialize(DictionaryController.databaseDictionary.getAllWords());
            words.addAll(DictionaryController.databaseDictionary.getAllWords());
            System.out.println("Initialize application successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager();
        initialize();
        sceneManager.loadAll();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = this.getClass().getResource("style/Style.css").toExternalForm();
        scene.getStylesheets().add(css);


        String icon = this.getClass().getResource("image/icon/icon.png").toExternalForm();

        boolean added = stage.getIcons().add(new Image(icon));
        stage.setTitle("Potato");
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}