package application;

import application.controller.DictionaryController;
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
            Tries.searchWord.addAll(DictionaryController.databaseDictionary.getAllWordsTarget());
            Tries.insertAllWordsIntoTries(Tries.searchWord);
            words.addAll(DictionaryController.databaseDictionary.getAllWords());
            System.out.println("Initialize application successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        initialize();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = this.getClass().getResource("style/Style.css").toExternalForm();
        scene.getStylesheets().add(css);


        String icon = this.getClass().getResource("image/icon.png").toExternalForm();

        boolean added = stage.getIcons().add(new Image(icon));
        stage.setTitle("Potato");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}