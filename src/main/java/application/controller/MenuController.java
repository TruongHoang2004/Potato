package application.controller;

import application.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class  MenuController extends ControllerSwitcher {
    @FXML
    private WebView github = new WebView();
    @FXML
    private Button exitGithub = new Button();
    @FXML
    private ImageView loading = new ImageView();

    private WebEngine webEngine;

    public void initialize() {
        github.getEngine().load("https://github.com/TruongHoang2004/Potato#readme");
    }

    public void openGithub(ActionEvent event) {
        loading.setVisible(true);
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        });
        future.thenRun(() -> {
            loading.setVisible(false);
            github.setVisible(true);
            exitGithub.setVisible(true);
        });
    }

    public void exitGithub(ActionEvent event) {
        github.setVisible(false);
        exitGithub.setVisible(false);
    }

    public void quit(ActionEvent event) {
        Platform.exit();
    }
}
