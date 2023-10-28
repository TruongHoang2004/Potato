package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneManager {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchScene(String sceneName, ActionEvent event) throws Exception {

        String path = "view/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("style/Style.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }
}
