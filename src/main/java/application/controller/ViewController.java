package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewController {

    @FXML
    public ImageView potatoImage() {
        Image icon = new Image("application/image/icon.png");
        ImageView image = new ImageView();
        image.setImage(icon);
        return  image;
    }

    @FXML
    Button game = new Button();

    public void gameButtonOnAction() {
        System.out.println("game");
    }


}
