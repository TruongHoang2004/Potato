package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
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